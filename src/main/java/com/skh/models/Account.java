package com.skh.models;

import java.math.BigDecimal;

public class Account {
	private long id;
	private String currency;
	private BigDecimal balance;

	public Account(long id, String currency, BigDecimal balance) {
		this.id = id;
		this.currency = currency;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
