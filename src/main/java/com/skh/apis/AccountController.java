package com.skh.apis;

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.skh.JsonTransformer;
import com.skh.models.Account;
import com.skh.services.AccountService;

public class AccountController {
	private AccountService accService;

	public AccountController(AccountService accService) {
		this.accService = accService;

		get("/account/:id", (req, res) -> {
			long id = Long.parseLong(req.params("id"));
			return this.accService.get(id);
		}, new JsonTransformer());

		post("/account", (req, res) -> {
			Account account = new Gson().fromJson(req.body(), Account.class);
			accService.create(account.getCurrency(), account.getBalance());
			return "";
		}, new JsonTransformer());
	}
}
