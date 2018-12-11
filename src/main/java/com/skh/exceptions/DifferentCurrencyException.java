package com.skh.exceptions;

public class DifferentCurrencyException extends Exception {
	private static final long serialVersionUID = 1L;

	public DifferentCurrencyException(String fromCurrency, String toCurrency) {
		super("Transaction currency should be one of [" + fromCurrency + ", " + toCurrency + "]");
	}
}
