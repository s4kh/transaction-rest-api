package com.skh.exceptions;

public class SameAccountTransactionException extends Exception {

	private static final long serialVersionUID = 1L;

	public SameAccountTransactionException() {
		super("Can't transfer between same accounts");
	}
}
