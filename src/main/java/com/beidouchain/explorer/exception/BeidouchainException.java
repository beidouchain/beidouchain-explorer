package com.beidouchain.explorer.exception;

public class BeidouchainException extends Exception {
	
	private static final long serialVersionUID = -2594564308191237107L;
	
	private String code;
	
	private String reason;

	public BeidouchainException(String code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "BeidouchainException [code=" + code + ", reason=" + reason + "]";
	}
	

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}	
	
	
}
