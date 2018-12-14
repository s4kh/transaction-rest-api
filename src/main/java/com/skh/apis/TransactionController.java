package com.skh.apis;

import static spark.Spark.post;

import com.google.gson.Gson;
import com.skh.JsonTransformer;
import com.skh.exceptions.SuccessResponse;
import com.skh.models.Transaction;
import com.skh.services.TransactionService;
import com.skh.utils.Filters;

public class TransactionController {
	private TransactionService trxService;

	public TransactionController(TransactionService trxService) {
		this.trxService = trxService;
	}

	public void getRoutes(Filters f) {
		post("/transfer", (req, res) -> {
			Transaction transaction = new Gson().fromJson(req.body(), Transaction.class);
			this.trxService.make(transaction);
			return new SuccessResponse("success");
		}, new JsonTransformer());

		f.filters();
	}
}
