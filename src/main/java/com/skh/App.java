package com.skh;

import static spark.Spark.get;

import com.skh.service.AccountService;
import com.skh.service.TransactionService;

public class App {

	private static AccountService accService;
	private static TransactionService trxService;
	
	public App(AccountService accService, TransactionService trxService) {
		this.accService = accService;
		this.trxService = trxService;
	}

	public static void main(String[] args) {

		get("/account/:id", (req, res) -> {
			long accId = Long.parseLong(req.params("id"));
			return accService.get(accId);
		});
	}
}
