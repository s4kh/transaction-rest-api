package com.skh.apis;

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
			JsonObject jsonAccount = new Gson().fromJson(req.body(), JsonObject.class);
			Account newAcc = accService.create(jsonAccount.get("currency").getAsString(), jsonAccount.get("amount").getAsBigDecimal());
			
			return newAcc;
		}, new JsonTransformer());
	}
}
