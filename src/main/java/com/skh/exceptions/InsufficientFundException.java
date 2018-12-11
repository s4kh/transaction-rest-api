package com.skh.exceptions;

public class InsufficientFundException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientFundException(long accountId) {
		super("Debit amount is more than account (" + accountId + ")  balance");
	}

}
