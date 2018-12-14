package com.skh.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.google.gson.Gson;
import com.skh.apis.TransactionController;
import com.skh.exceptions.ErrorResponse;
import com.skh.exceptions.Response;
import com.skh.exceptions.SuccessResponse;
import com.skh.models.Account;
import com.skh.models.Transaction;
import com.skh.services.AccountService;
import com.skh.services.AccountServiceImpl;
import com.skh.services.TransactionServiceImpl;
import com.skh.utils.CommonFilter;

import spark.Spark;
import spark.servlet.SparkApplication;

public class TransactionControllerTest {

	private static final AccountService accService = new AccountServiceImpl();
	private static final Gson gson = new Gson();

	public static class TransactionControllerTestApplication implements SparkApplication {
		@Override
		public void init() {
			new TransactionController(new TransactionServiceImpl(accService)).getRoutes(new CommonFilter());
		}
	}

	@ClassRule
	public static SparkServer<TransactionControllerTestApplication> testServer = new SparkServer<>(
	    TransactionControllerTestApplication.class, 4567);

	@Test
	public void testSameAccountTransfer() throws Exception {
		Account account = accService.create("MNT", BigDecimal.valueOf(3000));
		Transaction transaction = new Transaction(account.getId(), account.getId(), "MNT", BigDecimal.valueOf(1000));
		PostMethod post = testServer.post("/transfer", new Gson().toJson(transaction), false);
		HttpResponse httpResponse = testServer.execute(post);
		ErrorResponse response = gson.fromJson(new String(httpResponse.body()), ErrorResponse.class);

		assertEquals(400, httpResponse.code());
		assertEquals("Can't transfer between same accounts", response.getMessage());
		assertNotNull(testServer.getApplication());
	}

	@Test
	public void testInsufficientFundException() throws Exception {
		Account fromAcc = accService.create("MNT", BigDecimal.valueOf(100));
		Account toAcc = accService.create("MNT", BigDecimal.valueOf(100));
		BigDecimal debitAmt = BigDecimal.valueOf(5000);
		Transaction transaction = new Transaction(fromAcc.getId(), toAcc.getId(), "MNT", debitAmt);
		PostMethod post = testServer.post("/transfer", gson.toJson(transaction), false);
		HttpResponse httpResponse = testServer.execute(post);
		Response response = gson.fromJson(new String(httpResponse.body()), ErrorResponse.class);
		assertEquals(400, httpResponse.code());
		assertEquals("Debit amount(" + debitAmt.toString() + ") is more than account (" + fromAcc.getId() + ")  balance ("
		    + fromAcc.getBalance() + ")", response.getMessage());

	}

	@Test
	public void testSameCurrencyTransaction() throws Exception {
		Account fromAcc = accService.create("MNT", new BigDecimal(3000));
		Account toAcc = accService.create("MNT", new BigDecimal(3000));

		Transaction trx = new Transaction(fromAcc.getId(), toAcc.getId(), "MNT", new BigDecimal(2000));
		PostMethod post = testServer.post("/transfer", gson.toJson(trx), false);
		HttpResponse httpResponse = testServer.execute(post);
		Response response = gson.fromJson(new String(httpResponse.body()), SuccessResponse.class);

		assertEquals(200, httpResponse.code());
		assertEquals("success", response.getMessage());
	}

	@Test
	public void testWrongCurrencyTransaction() throws Exception {
		Account fromAcc = accService.create("MNT", new BigDecimal(3000));
		Account toAcc = accService.create("EUR", new BigDecimal(3000));

		Transaction trx = new Transaction(fromAcc.getId(), toAcc.getId(), "USD", new BigDecimal(2000));
		PostMethod post = testServer.post("/transfer", gson.toJson(trx), false);
		HttpResponse httpResponse = testServer.execute(post);
		Response response = gson.fromJson(new String(httpResponse.body()), ErrorResponse.class);

		assertEquals(400, httpResponse.code());
		assertEquals("Transaction currency should be one of [" + fromAcc.getCurrency() + ", " + toAcc.getCurrency() + "]",
		    response.getMessage());
	}

	@AfterClass
	public static void afterClass() throws Exception {
		Spark.stop();
		Thread.sleep(1000); // Wait for it to stop, so other controller tests can start a server
	}
}
