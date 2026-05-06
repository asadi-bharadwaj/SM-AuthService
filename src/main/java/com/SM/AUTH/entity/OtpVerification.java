package com.SM.AUTH.entity;

import java.time.LocalDateTime;

import com.SM.AUTH.util.OtpPurpose;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "otp_verifications")
public class OtpVerification {

	@Id
    private String id;

    @Indexed
    private String target;

    private String otpCode;

    private OtpPurpose purpose;

    private LocalDateTime expiresAt;

    private Boolean verified = false;

    private Integer attempts = 0;

    private LocalDateTime createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public OtpPurpose getPurpose() {
		return purpose;
	}

	public void setPurpose(OtpPurpose purpose) {
		this.purpose = purpose;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public OtpVerification(String id, String target, String otpCode, OtpPurpose purpose, LocalDateTime expiresAt,
			Boolean verified, Integer attempts, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.target = target;
		this.otpCode = otpCode;
		this.purpose = purpose;
		this.expiresAt = expiresAt;
		this.verified = verified;
		this.attempts = attempts;
		this.createdAt = createdAt;
	}

	public OtpVerification() {
		super();
	}
    
    
}
