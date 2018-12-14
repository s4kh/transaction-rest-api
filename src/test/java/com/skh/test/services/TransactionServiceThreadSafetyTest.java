package com.skh.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.skh.models.Account;
import com.skh.models.Transaction;
import com.skh.services.AccountService;
import com.skh.services.AccountServiceImpl;
import com.skh.services.TransactionService;
import com.skh.services.TransactionServiceImpl;

public class TransactionServiceThreadSafetyTest {

	private static final int NUM_THREADS = 100;

	@Test
	public void testTransferWithMultipleThreads() throws Exception {
		AccountService accService = new AccountServiceImpl();
		TransactionService trxService = new TransactionServiceImpl(accService);

		Account fromAcc = accService.create("MNT", new BigDecimal(10000));
		Account toAcc = accService.create("MNT", new BigDecimal(100));

		ExecutorService es = Executors.newFixedThreadPool(NUM_THREADS);
		final CountDownLatch latch = new CountDownLatch(NUM_THREADS);

		for (int i = 0; i < NUM_THREADS; i++) {
			es.execute(() -> {
				try {
					Transaction trx = new Transaction(fromAcc.getId(), toAcc.getId(), "MNT", BigDecimal.TEN);
					trxService.make(trx);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		assertEquals(accService.get(fromAcc.getId()).getBalance(), BigDecimal.valueOf(9000));
		assertEquals(accService.get(toAcc.getId()).getBalance(), BigDecimal.valueOf(1100));
	}

}