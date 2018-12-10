package com.skh.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.skh.model.Account;

public class AccountServiceImpl implements AccountService {

	private final HashMap<Long, Account> accounts = new HashMap<>();

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
			throw new Exception("Account with " + id + " id does not exist");
		}

		return account;
	}

}
