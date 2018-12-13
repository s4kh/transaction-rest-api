package com.skh.test.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.skh.exceptions.AccountDoesNotExistException;
import com.skh.exceptions.DifferentCurrencyException;
import com.skh.exceptions.SameAccountTransactionException;
import com.skh.models.Account;
import com.skh.models.Transaction;
import com.skh.services.AccountService;
import com.skh.services.TransactionService;
import com.skh.services.TransactionServiceImpl;
import com.skh.utils.CurrencyConverter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {
	private AccountService accountService = mock(AccountService.class);
	private TransactionService trxService = new TransactionServiceImpl(accountService);

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testAccountDoesNotExistTransaction() throws Exception {
		thrown.expect(AccountDoesNotExistException.class);
		thrown.expectMessage("Account does not exist on id:0");
		trxService.make(new Transaction(0L, 0L, "MNT", new BigDecimal(1000)));
	}

	@Test
	public void testSameAccountTransaction() throws Exception {
		Account account = new Account(1L, "MNT", new BigDecimal(10000));
		when(accountService.get(1L)).thenReturn(account);

		thrown.expect(SameAccountTransactionException.class);
		thrown.expectMessage("Can't transfer between same accounts");
		trxService.make(new Transaction(1L, 1L, "MNT", new BigDecimal(1000)));
	}

	@Test
	public void testDifferentCurrencyException() throws Exception {
		Account fromAcc = new Account(0L, "MNT", new BigDecimal(10000));
		Account toAcc = new Account(1L, "EUR", new BigDecimal(10000));

		when(accountService.get(0L)).thenReturn(fromAcc);
		when(accountService.get(1L)).thenReturn(toAcc);

		thrown.expect(DifferentCurrencyException.class);
		thrown.expectMessage(
				"Transaction currency should be one of [" + fromAcc.getCurrency() + ", " + toAcc.getCurrency() + "]");
		trxService.make(new Transaction(0L, 1L, "USD", new BigDecimal(1000)));
	}

	@Test
	public void testSameCurrencyTransaction() throws Exception {
		Account fromAcc = new Account(0L, "MNT", new BigDecimal(3000));
		Account toAcc = new Account(1L, "MNT", new BigDecimal(3000));

		when(accountService.get(0L)).thenReturn(fromAcc);
		when(accountService.get(1L)).thenReturn(toAcc);

		Transaction trx = new Transaction(0L, 1L, "MNT", new BigDecimal(2000));
		trxService.make(trx);

		assertEquals(new BigDecimal(1000), fromAcc.getBalance());
		assertEquals(new BigDecimal(5000), toAcc.getBalance());
	}

	@Test
	public void testDifferentCurrencyTransaction() throws Exception {
		BigDecimal fromAccInitialBalance = new BigDecimal(10000);
		BigDecimal toAccInitialBalance = new BigDecimal(10000);
		Account fromAcc = new Account(0L, "MNT", fromAccInitialBalance);
		Account toAcc = new Account(1L, "USD", toAccInitialBalance);
		BigDecimal trxAmt = new BigDecimal(2);

		BigDecimal convertedAmt = CurrencyConverter.convert("USD", "MNT", trxAmt);

		when(accountService.get(0L)).thenReturn(fromAcc);
		when(accountService.get(1L)).thenReturn(toAcc);

		Transaction trx = new Transaction(0L, 1L, "USD", trxAmt);
		trxService.make(trx);

		assertEquals(fromAccInitialBalance.subtract(convertedAmt), fromAcc.getBalance());
		assertEquals(toAccInitialBalance.add(trxAmt), toAcc.getBalance());
	}

}
