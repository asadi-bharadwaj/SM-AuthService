package com.SM.AUTH.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private UserAuth user;

	    @Column(nullable = false, length = 500)
	    private String token;

	    @Column(name = "device_id", length = 100)
	    private String deviceId;

	    @Column(name = "ip_address", length = 50)
	    private String ipAddress;

	    @Column(name = "expires_at", nullable = false)
	    private LocalDateTime expiresAt;

	    private Boolean revoked = false;

	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt;

	    @PrePersist
	    public void prePersist() {
	        createdAt = LocalDateTime.now();
	    }

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

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		public LocalDateTime getExpiresAt() {
			return expiresAt;
		}

		public void setExpiresAt(LocalDateTime expiresAt) {
			this.expiresAt = expiresAt;
		}

		public Boolean getRevoked() {
			return revoked;
		}

		public void setRevoked(Boolean revoked) {
			this.revoked = revoked;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public RefreshToken(Long id, UserAuth user, String token, String deviceId, String ipAddress,
				LocalDateTime expiresAt, Boolean revoked, LocalDateTime createdAt) {
			super();
			this.id = id;
			this.user = user;
			this.token = token;
			this.deviceId = deviceId;
			this.ipAddress = ipAddress;
			this.expiresAt = expiresAt;
			this.revoked = revoked;
			this.createdAt = createdAt;
		}

		public RefreshToken() {
			super();
		}
	    
	    
}
