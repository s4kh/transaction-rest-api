package com.skh.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skh.exceptions.AccountDoesNotExistException;
import com.skh.exceptions.DifferentCurrencyException;
import com.skh.exceptions.InsufficientFundException;
import com.skh.exceptions.MinusTransactionAmountException;
import com.skh.exceptions.SameAccountTransactionException;
import com.skh.models.Account;
import com.skh.models.Transaction;
import com.skh.utils.CurrencyConverter;

public class TransactionServiceImpl implements TransactionService {

	private final AccountService accService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	public TransactionServiceImpl(AccountService accService) {
		this.accService = accService;
	}

	@Override
	public void make(Transaction transaction) throws Exception {

		if (transaction == null) {
			throw new Exception("Empty transaction");
		}

		if (transaction.getAmount().compareTo(new BigDecimal(0)) < 0) {
			throw new MinusTransactionAmountException(transaction.getAmount());
		}

		Account fromAcc = accService.get(transaction.getFromAcc());
		Account toAcc = accService.get(transaction.getToAcc());
		String trxCurrency = transaction.getCurrency();

		if (fromAcc == null) {
			throw new AccountDoesNotExistException(transaction.getFromAcc());
		}

		if (toAcc == null) {
			throw new AccountDoesNotExistException(transaction.getToAcc());
		}

		if (fromAcc.getId() == toAcc.getId()) {
			throw new SameAccountTransactionException();
		}

		if (!trxCurrency.equals(fromAcc.getCurrency()) && !trxCurrency.equals(toAcc.getCurrency())) {
			throw new DifferentCurrencyException(fromAcc.getCurrency(), toAcc.getCurrency());
		}

		BigDecimal trxAmt = transaction.getAmount();
		BigDecimal debitAmt = trxAmt;
		BigDecimal creditAmt = trxAmt;

		if (!trxCurrency.equals(fromAcc.getCurrency())) {
			debitAmt = CurrencyConverter.convert(trxCurrency, fromAcc.getCurrency(), trxAmt);
		} else if (!trxCurrency.equals(toAcc.getCurrency())) {
			creditAmt = CurrencyConverter.convert(trxCurrency, toAcc.getCurrency(), trxAmt);
		}

		if (debitAmt.compareTo(BigDecimal.valueOf(-1)) == 0 || creditAmt.compareTo(BigDecimal.valueOf(-1)) == 0) {
			throw new Exception("Currency server unavailable, try with same currency");
		}

		transactBtwAccounts(fromAcc, toAcc, debitAmt, creditAmt);
	}

	private synchronized void transactBtwAccounts(Account fromAcc, Account toAcc, BigDecimal debitAmt,
	    BigDecimal creditAmt) throws InsufficientFundException {
		if (debitAmt.compareTo(fromAcc.getBalance()) > 0) {
			throw new InsufficientFundException(fromAcc, debitAmt);
		}

		fromAcc.setBalance(fromAcc.getBalance().subtract(debitAmt));
		toAcc.setBalance(toAcc.getBalance().add(creditAmt));

		LOGGER.info("from {} to {}", fromAcc, toAcc);
	}

}
