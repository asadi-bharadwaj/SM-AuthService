package com.SM.AUTH.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "refresh_tokens")
public class RefreshToken {

	 	@Id
	    private String id;

	    private UserAuth user;

	    @Indexed
	    private String token;

	    private String deviceId;

	    private String ipAddress;

	    private LocalDateTime expiresAt;

	    private Boolean revoked = false;

	    private LocalDateTime createdAt;

		public String getId() {
			return id;
		}

		public void setId(String id) {
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

		public RefreshToken(String id, UserAuth user, String token, String deviceId, String ipAddress,
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
