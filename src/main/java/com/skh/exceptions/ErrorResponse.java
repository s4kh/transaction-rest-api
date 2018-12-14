package com.skh.exceptions;

public class ErrorResponse extends Response {

	public ErrorResponse(Exception e) {
		super(e.getMessage());
	}
}
