package com.skh.exceptions;

import java.math.BigDecimal;

public class MinusTransactionAmountException extends Exception {

	private static final long serialVersionUID = 1L;

	public MinusTransactionAmountException(BigDecimal amt) {
		super("Cannot make a transaction with minus amount: " + amt.toString());
	}
}
