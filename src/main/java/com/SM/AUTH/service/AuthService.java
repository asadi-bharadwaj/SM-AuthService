package com.SM.AUTH.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.SM.UserService.Repository.UserProfileRepository;
import com.SM.UserService.entity.UserProfile;


@Service
public class AuthService {
	

    private  UserAuthRepository userRepo;
    private  RefreshTokenRepository refreshRepo;
    private  OtpVerificationRepository otpRepo;
    private  PasswordEncoder encoder;
    private  JwtUtil jwtUtil;
    private UserProfileRepository profileRepo;
    public AuthService(
            UserAuthRepository userRepo,
            RefreshTokenRepository refreshRepo,
            OtpVerificationRepository otpRepo,
            UserProfileRepository profileRepo,
            PasswordEncoder encoder,
            JwtUtil jwtUtil) {

        this.userRepo = userRepo;
        this.refreshRepo = refreshRepo;
        this.otpRepo = otpRepo;
        this.profileRepo = profileRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }
    public String register(RegisterRequest req) {

        if (userRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already exists");

        UserAuth user = new UserAuth();
        user.setUuid(UUID.randomUUID().toString());
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setPasswordHash(encoder.encode(req.getPassword()));
        user.setRole(Role.USER);

        UserAuth savedUser = userRepo.save(user);

        // Create profile immediately
        UserProfile profile = new UserProfile();
        profile.setAuthUserId(savedUser.getId());
        profile.setUsername(savedUser.getUsername());
        profile.setDisplayName(savedUser.getUsername());
        profile.setBio("");
        profile.setAvatarUrl("");
        profile.setCountry("");
        profile.setLanguage("");

        if (!profileRepo.findByAuthUserId(savedUser.getId()).isPresent()) {
        	   profileRepo.save(profile);
        	}

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

        return new AuthResponse(user.getId(), access, refresh);
    }

    public AuthResponse refresh(RefreshRequest req) {

        RefreshToken token = refreshRepo.findByToken(req.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        String access =
            jwtUtil.generateAccessToken(token.getUser().getEmail());

        return new AuthResponse(access, token.getToken());
    }

    public String sendOtp(OtpRequest req) {

        String otp =
            String.valueOf((int)(Math.random() * 900000) + 100000);

        OtpVerification entity = new OtpVerification();
        entity.setTarget(req.getTarget());
        entity.setOtpCode(otp);
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(entity);

        System.out.println("OTP => " + otp);

        return "OTP Sent";
    }

    public AuthResponse verifyOtp(VerifyOtpRequest req) {

        OtpVerification otp = otpRepo
                .findTopByTargetOrderByIdDesc(req.getTarget())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!otp.getOtpCode().equals(req.getOtp()))
            throw new RuntimeException("Wrong OTP");

        String access =
            jwtUtil.generateAccessToken(req.getTarget());

        String refresh =
            jwtUtil.generateRefreshToken(req.getTarget());

        return new AuthResponse(access, refresh);
    }
}