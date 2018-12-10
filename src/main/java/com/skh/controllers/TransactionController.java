package com.skh.controllers;

import static spark.Spark.post;

import com.google.gson.Gson;
import com.skh.model.Transaction;
import com.skh.service.TransactionService;

public class TransactionController {
	private TransactionService trxService;

	public TransactionController(TransactionService trxService) {
		this.trxService = trxService;
	}

	public void getRoutes() {
		post("/transfer", (req, res) -> {
			Transaction transaction = new Gson().fromJson(req.body(), Transaction.class);
			this.trxService.make(transaction);
			return "";
		});
	}
}
