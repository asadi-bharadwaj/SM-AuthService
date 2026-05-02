package com.SM.AUTH.dto;

public class AuthResponse {
	private Long id;
	private String accessToken;
	private String refreshToken;

	public Long getId() {
		return id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public AuthResponse(Long id, String accessToken, String refreshToken) {
		this.id = id;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public AuthResponse( String accessToken, String refreshToken) {
	
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}