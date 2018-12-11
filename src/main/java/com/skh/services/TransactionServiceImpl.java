package com.skh.services;

import com.skh.exceptions.AccountDoesNotExistException;
import com.skh.exceptions.DifferentCurrencyException;
import com.skh.exceptions.SameAccountTransactionException;
import com.skh.models.Account;
import com.skh.models.Transaction;

public class TransactionServiceImpl implements TransactionService {

	private final AccountService accService;

	public TransactionServiceImpl(AccountService accService) {
		this.accService = accService;
	}

	@Override
	public void make(Transaction transaction) throws Exception {
		/**
		 * TODO: check same account, check insufficient fund, check do accounts exist,
		 * convert to currencies, lock
		 */

		if (transaction == null) {
			throw new Exception("Empty transaction");
		}

		if (transaction.getFromAcc() == transaction.getToAcc()) {
			throw new SameAccountTransactionException();
		}

		Account fromAcc = accService.get(transaction.getFromAcc());
		Account toAcc = accService.get(transaction.getToAcc());

		if (fromAcc == null) {
			throw new AccountDoesNotExistException(transaction.getFromAcc());
		}

		if (toAcc == null) {
			throw new AccountDoesNotExistException(transaction.getToAcc());
		}

		if (!transaction.getCurrency().equals(fromAcc.getCurrency())
		    && !transaction.getCurrency().equals(toAcc.getCurrency())) {
			throw new DifferentCurrencyException(fromAcc.getCurrency(), toAcc.getCurrency());
		}

	}

}
