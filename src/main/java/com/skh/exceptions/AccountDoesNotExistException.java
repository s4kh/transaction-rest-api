package com.skh.exceptions;

public class AccountDoesNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public AccountDoesNotExistException(long accountId) {
		super("Account does not exist on id:" + accountId);
	}
}
