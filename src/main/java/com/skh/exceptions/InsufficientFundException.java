package com.skh.exceptions;

import java.math.BigDecimal;

import com.skh.models.Account;

public class InsufficientFundException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientFundException(Account acc, BigDecimal debitAmt) {
		super("Debit amount(" + debitAmt.toString() + ") is more than account (" + acc.getId() + ")  balance ("
				+ acc.getBalance() + ")");
	}

}
