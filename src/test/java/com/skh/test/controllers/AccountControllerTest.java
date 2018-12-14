package com.skh.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.google.gson.Gson;
import com.skh.apis.AccountController;
import com.skh.exceptions.ErrorResponse;
import com.skh.exceptions.Response;
import com.skh.models.Account;
import com.skh.services.AccountServiceImpl;
import com.skh.utils.CommonFilter;

import spark.Spark;
import spark.servlet.SparkApplication;

public class AccountControllerTest {
	private static final Gson gson = new Gson();

	public static class AccountControllerTestApplication implements SparkApplication {
		@Override
		public void init() {
			new AccountController(new AccountServiceImpl()).getRoutes(new CommonFilter());
		}
	}

	@ClassRule
	public static SparkServer<AccountControllerTestApplication> testServer = new SparkServer<>(
	    AccountControllerTestApplication.class, 4567);

	@Test
	public void testAccountDoesExist() throws Exception {
		/* The second parameter indicates whether redirects must be followed or not */
		GetMethod get = testServer.get("/account/0", false);
		HttpResponse httpResponse = testServer.execute(get);
		Response response = gson.fromJson(new String(httpResponse.body()), ErrorResponse.class);

		assertEquals(400, httpResponse.code());
		assertEquals("Account does not exist on id:0", response.getMessage());
		assertNotNull(testServer.getApplication());
	}

	@Test
	public void testCreateAccount() throws Exception {
		Account acc = new Account("MNT", new BigDecimal(1000.33));
		PostMethod postMethod = testServer.post("/account", gson.toJson(acc), false);
		postMethod.addHeader("Content-Type", "application/json");
		HttpResponse httpResponse = testServer.execute(postMethod);
		Account actual = gson.fromJson(new String(httpResponse.body()), Account.class);

		assertEquals(200, httpResponse.code());
		assertEquals(acc.getCurrency(), actual.getCurrency());
		assertEquals(acc.getBalance(), actual.getBalance());
		assertNotNull(testServer.getApplication());
	}

	@AfterClass
	public static void afterClass() throws Exception {
		Spark.stop();
		Thread.sleep(1000);// Wait for it to finish stopping, so other controller tests can start a server
	}

}
