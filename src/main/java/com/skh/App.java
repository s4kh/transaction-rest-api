package com.skh;

import com.skh.apis.AccountController;
import com.skh.apis.TransactionController;
import com.skh.services.AccountServiceImpl;
import com.skh.services.TransactionServiceImpl;
import com.skh.utils.CommonFilter;
import com.skh.utils.Filters;

public class App {

	public static void main(String[] args) {
		AccountServiceImpl accService = new AccountServiceImpl();
		TransactionServiceImpl trxService = new TransactionServiceImpl(accService);
		Filters commonFilters = new CommonFilter();
		new AccountController(accService).getRoutes(commonFilters);
		new TransactionController(trxService).getRoutes(commonFilters);
	}
}
