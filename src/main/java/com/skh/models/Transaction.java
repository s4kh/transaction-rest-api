package com.skh.models;

import java.math.BigDecimal;

public class Transaction {
	private long fromAcc;
	private long toAcc;
	private String currency;
	private BigDecimal amount;

	public Transaction(long fromAcc, long toAcc, String curr, BigDecimal amt) {
		this.fromAcc = fromAcc;
		this.toAcc = toAcc;
		this.currency = curr;
		this.amount = amt;
	}

	public long getFromAcc() {
		return fromAcc;
	}

	public void setFromAcc(long fromAcc) {
		this.fromAcc = fromAcc;
	}

	public long getToAcc() {
		return toAcc;
	}

	public void setToAcc(long toAcc) {
		this.toAcc = toAcc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
