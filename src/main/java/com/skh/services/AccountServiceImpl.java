package com.skh.services;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.skh.exceptions.AccountDoesNotExistException;
import com.skh.models.Account;

public class AccountServiceImpl implements AccountService {

	private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

	private final AtomicLong accountIdGenerator = new AtomicLong();

	@Override
	public Account create(String currency, BigDecimal balance) {
		long accId = accountIdGenerator.incrementAndGet();
		Account newAcc = new Account(accId, currency, balance);
		accounts.put(accId, newAcc);

		return newAcc;
	}

	@Override
	public Account get(long id) throws Exception {
		Account account = accounts.get(id);
		if (account == null) {
			throw new AccountDoesNotExistException(id);
		}

		return account;
	}

}
