package com.skh;

import com.skh.controllers.AccountController;
import com.skh.controllers.TransactionController;
import com.skh.service.AccountServiceImpl;
import com.skh.service.TransactionServiceImpl;

public class App {

	public static void main(String[] args) {
		AccountServiceImpl accService = new AccountServiceImpl();
		TransactionServiceImpl trxService = new TransactionServiceImpl();
		new AccountController(accService);
		new TransactionController(trxService);
	}
}
