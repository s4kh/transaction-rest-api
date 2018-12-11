package com.skh.exceptions;

public class ErrorResponse {

	private String message;


	public ErrorResponse(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return this.message;
	}
}
