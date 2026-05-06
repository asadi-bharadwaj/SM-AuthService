package com.SM.AUTH.entity;

import java.time.LocalDateTime;

import com.SM.AUTH.util.LoginStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "login_audit_logs")
class LoginAuditLog {

    @Id
    private Long id;

    private Long userId;

    private String emailOrPhone;

    private LoginStatus status;

    private String reason;

    private String ipAddress;

    private String userAgent;

    private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmailOrPhone() {
		return emailOrPhone;
	}

	public void setEmailOrPhone(String emailOrPhone) {
		this.emailOrPhone = emailOrPhone;
	}

	public LoginStatus getStatus() {
		return status;
	}

	public void setStatus(LoginStatus status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LoginAuditLog(Long id, Long userId, String emailOrPhone, LoginStatus status, String reason, String ipAddress,
			String userAgent, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.emailOrPhone = emailOrPhone;
		this.status = status;
		this.reason = reason;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
		this.createdAt = createdAt;
	}

	public LoginAuditLog() {
		super();
	}
    
    
}