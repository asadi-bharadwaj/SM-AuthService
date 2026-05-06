package com.SM.AUTH.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "password_reset_tokens")
public class PasswordResetToken {

	@Id
    private Long id;

    private UserAuth user;

    private String token;

    private LocalDateTime expiresAt;

    private Boolean usedFlag = false;

    private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAuth getUser() {
		return user;
	}

	public void setUser(UserAuth user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Boolean getUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(Boolean usedFlag) {
		this.usedFlag = usedFlag;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public PasswordResetToken(Long id, UserAuth user, String token, LocalDateTime expiresAt, Boolean usedFlag,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.user = user;
		this.token = token;
		this.expiresAt = expiresAt;
		this.usedFlag = usedFlag;
		this.createdAt = createdAt;
	}

	public PasswordResetToken() {
		super();
	}
    
    
}
