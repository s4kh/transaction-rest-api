package com.skh;

import com.skh.apis.AccountController;
import com.skh.apis.TransactionController;
import com.skh.services.AccountServiceImpl;
import com.skh.services.TransactionServiceImpl;

public class App {

	public static void main(String[] args) {
		AccountServiceImpl accService = new AccountServiceImpl();
		TransactionServiceImpl trxService = new TransactionServiceImpl(accService);
		new AccountController(accService);
		new TransactionController(trxService);
	}
}
