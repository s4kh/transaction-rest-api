package com.skh.service;

import java.math.BigDecimal;

public interface TransactionService {
	void transfer(long fromAcc, long toAcc, BigDecimal amount, String currency);
}
