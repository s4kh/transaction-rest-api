package com.skh;

import static spark.Spark.after;
import static spark.Spark.exception;

import com.google.gson.Gson;
import com.skh.apis.AccountController;
import com.skh.apis.TransactionController;
import com.skh.exceptions.ErrorResponse;
import com.skh.services.AccountServiceImpl;
import com.skh.services.TransactionServiceImpl;

public class App {

	public static void main(String[] args) {
		AccountServiceImpl accService = new AccountServiceImpl();
		TransactionServiceImpl trxService = new TransactionServiceImpl(accService);
		new AccountController(accService);
		new TransactionController(trxService);

		after((req, res) -> {
			res.type("application/json");
		});

		exception(Exception.class, (e, req, res) -> {
			res.status(400);
			res.type("application/json");
			res.body(new Gson().toJson(new ErrorResponse(e)));
		});
	}  
}
