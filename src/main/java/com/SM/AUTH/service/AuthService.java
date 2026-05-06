package com.SM.AUTH.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.SM.AUTH.dto.AuthResponse;
import com.SM.AUTH.dto.LoginRequest;
import com.SM.AUTH.dto.OtpRequest;
import com.SM.AUTH.dto.RefreshRequest;
import com.SM.AUTH.dto.RegisterRequest;
import com.SM.AUTH.dto.VerifyOtpRequest;
import com.SM.AUTH.entity.OtpVerification;
import com.SM.AUTH.entity.RefreshToken;
import com.SM.AUTH.entity.UserAuth;
import com.SM.AUTH.repository.OtpVerificationRepository;
import com.SM.AUTH.repository.RefreshTokenRepository;
import com.SM.AUTH.repository.UserAuthRepository;
import com.SM.AUTH.security.JwtUtil;
import com.SM.AUTH.util.Role;

@Service
public class AuthService {

    private final UserAuthRepository userRepo;
    private final RefreshTokenRepository refreshRepo;
    private final OtpVerificationRepository otpRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    // More robust email regex: allows most common characters and subdomains
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    // Regex for Password: Min 8 chars, at least 1 special char, 1 number, 1 uppercase
    private static final String PWD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    public AuthService(
            UserAuthRepository userRepo,
            RefreshTokenRepository refreshRepo,
            OtpVerificationRepository otpRepo,
            PasswordEncoder encoder,
            JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.refreshRepo = refreshRepo;
        this.otpRepo = otpRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    private void validate(String email, String password) {
        System.out.println("Validating Email: [" + email + "]");
        if (email == null || !Pattern.compile(EMAIL_REGEX).matcher(email.trim()).matches()) {
            throw new RuntimeException("Invalid email format. Ensure there are no spaces and you use a valid domain (e.g., test@gmail.com)");
        }
        if (password == null || !Pattern.matches(PWD_REGEX, password.trim())) {
            throw new RuntimeException("Password must be at least 8 characters, include a number, uppercase letter, and a special character.");
        }
    }

    public String startRegistration(RegisterRequest req) {
        String email = req.getEmail() != null ? req.getEmail().trim() : null;
        String password = req.getPassword() != null ? req.getPassword().trim() : null;

        // 1. Validate Email & Password
        validate(email, password);

        // 2. Check if user already exists
        if (userRepo.existsByEmail(email))
            throw new RuntimeException("Email already exists");

        // 3. Generate and Send OTP
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        OtpVerification entity = new OtpVerification();
        entity.setTarget(email);
        entity.setOtpCode(otp);
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        otpRepo.save(entity);

        sendNotification(email, "EMAIL", "Verify your Registration", 
            "Your OTP code for ShowMe registration is: " + otp);

        return "OTP sent to your email. Please verify to complete registration.";
    }

    public String completeRegistration(RegisterRequest req) {
        String email = req.getEmail() != null ? req.getEmail().trim() : null;
        
        // 1. Verify OTP
        OtpVerification otpEntity = otpRepo.findTopByTargetOrderByIdDesc(email)
                .orElseThrow(() -> new RuntimeException("OTP not found or expired"));

        if (!otpEntity.getOtpCode().equals(req.getOtp())) {
            throw new RuntimeException("Invalid OTP code");
        }

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired. Please request a new one.");
        }

        // 2. Create User
        UserAuth user = new UserAuth();
        user.setUuid(UUID.randomUUID().toString());
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setPasswordHash(encoder.encode(req.getPassword()));
        user.setRole(Role.USER);

        UserAuth savedUser = userRepo.save(user);

        // 3. Call USER service to create profile
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> body = new HashMap<>();
            body.put("authUserId", savedUser.getId());
            body.put("username", savedUser.getUsername());
            restTemplate.postForObject("http://localhost:8083/users/init-profile", body, String.class);
        } catch (Exception e) {
            System.err.println("Profile creation failed: " + e.getMessage());
        }

        // 4. Send Welcome Email
        sendNotification(user.getEmail(), "EMAIL", "Welcome to ShowMe!", 
            "Hi " + user.getUsername() + ", your account has been verified and created successfully.");

        return "Registered Successfully";
    }

    public AuthResponse login(LoginRequest req) {
        UserAuth user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPasswordHash()))
            throw new RuntimeException("Invalid Password");

        String access = jwtUtil.generateAccessToken(user.getEmail());
        String refresh = jwtUtil.generateRefreshToken(user.getEmail());

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(refresh);
        token.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshRepo.save(token);

        // Send Login Notification
        sendNotification(user.getEmail(), "EMAIL", "New Login Detected", 
            "Hi " + user.getUsername() + ", you have successfully logged into your ShowMe account.");

        return new AuthResponse(user.getId(), access, refresh);
    }

    private void sendNotification(String recipientId, String type, String title, String message) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> body = new HashMap<>();
            body.put("recipientId", recipientId);
            body.put("type", type);
            body.put("title", title);
            body.put("message", message);
            restTemplate.postForObject("http://localhost:8084/api/notifications", body, String.class);
        } catch (Exception e) {
            System.err.println("Notification failed: " + e.getMessage());
        }
    }

    public AuthResponse refresh(RefreshRequest req) {
        RefreshToken token = refreshRepo.findByToken(req.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        String access = jwtUtil.generateAccessToken(token.getUser().getEmail());
        return new AuthResponse(access, token.getToken());
    }

    public String sendOtp(OtpRequest req) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        OtpVerification entity = new OtpVerification();
        entity.setTarget(req.getTarget());
        entity.setOtpCode(otp);
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otpRepo.save(entity);
        System.out.println("OTP => " + otp);
        return "OTP Sent";
    }

    public AuthResponse verifyOtp(VerifyOtpRequest req) {
        OtpVerification otp = otpRepo.findTopByTargetOrderByIdDesc(req.getTarget())
                .orElseThrow(() -> new RuntimeException("OTP not found"));
        if (!otp.getOtpCode().equals(req.getOtp()))
            throw new RuntimeException("Wrong OTP");
        String access = jwtUtil.generateAccessToken(req.getTarget());
        String refresh = jwtUtil.generateRefreshToken(req.getTarget());
        return new AuthResponse(access, refresh);
    }
}
