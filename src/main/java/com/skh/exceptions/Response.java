package com.skh.exceptions;

public abstract class Response {
	private String message;

	public Response(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
