package com.skh.spark.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.skh.exceptions.AccountDoesNotExistException;
import com.skh.models.Account;
import com.skh.services.AccountService;
import com.skh.services.AccountServiceImpl;

public class AccountServiceTest {
	private static final AccountService accService = new AccountServiceImpl();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void beforeClass() {
		accService.create("MNT", new BigDecimal("4000.33"));
		accService.create("USD", new BigDecimal("300"));
	}

	@Test
	public void testGetAccount() throws Exception {
		Account actual = accService.get(1);

		assertEquals(1L, actual.getId());
		assertEquals("MNT", actual.getCurrency());
		assertEquals(new BigDecimal("4000.33"), actual.getBalance());
	}

	@Test
	public void testAccountDoesNotExist() throws Exception {
		thrown.expect(AccountDoesNotExistException.class);
		thrown.expectMessage("Account does not exist on id:6");
		accService.get(6);
	}

	@Test
	public void testCreateAccount() {
		BigDecimal expectedAmt = new BigDecimal("5000");
		Account newAcc = accService.create("MNT", expectedAmt);

		assertEquals(3, newAcc.getId());
		assertEquals("MNT", newAcc.getCurrency());
		assertEquals(expectedAmt, newAcc.getBalance());

	}

}
