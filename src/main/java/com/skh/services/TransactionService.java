package com.skh.services;

import com.skh.models.Transaction;

public interface TransactionService {
	void make(Transaction transaction) throws Exception;
}
