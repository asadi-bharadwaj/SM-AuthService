package com.SM.AUTH.entity;

import java.time.LocalDateTime;

import com.SM.AUTH.util.AccountStatus;
import com.SM.AUTH.util.Provider;
import com.SM.AUTH.util.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users_auth")
public class UserAuth {
	@Id
    private Long id;

    private String uuid;

    @Indexed
    private String email;

    @Indexed
    private String phone;

    @Indexed
    private String username;

    private String passwordHash;

    private Role role = Role.USER;

    private Provider provider = Provider.LOCAL;

    private Boolean isEmailVerified = false;

    private Boolean isPhoneVerified = false;

    private AccountStatus status = AccountStatus.ACTIVE;

    private Integer failedAttempts = 0;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public Boolean getIsPhoneVerified() {
		return isPhoneVerified;
	}

	public void setIsPhoneVerified(Boolean isPhoneVerified) {
		this.isPhoneVerified = isPhoneVerified;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public Integer getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(LocalDateTime lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserAuth(Long id, String uuid, String email, String phone, String username, String passwordHash, Role role,
			Provider provider, Boolean isEmailVerified, Boolean isPhoneVerified, AccountStatus status,
			Integer failedAttempts, LocalDateTime lastLoginAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
		this.provider = provider;
		this.isEmailVerified = isEmailVerified;
		this.isPhoneVerified = isPhoneVerified;
		this.status = status;
		this.failedAttempts = failedAttempts;
		this.lastLoginAt = lastLoginAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UserAuth() {
		super();
	}
    
    
    
}