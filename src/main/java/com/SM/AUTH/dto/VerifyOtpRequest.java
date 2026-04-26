package com.SM.AUTH.dto;

public class VerifyOtpRequest {
    private String target;
    private String otp;
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
    
    
}