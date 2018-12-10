package com.skh.controllers;

import static spark.Spark.get;

import com.skh.service.AccountService;

public class AccountController {
	private AccountService accService;

	public AccountController(AccountService accService) {
		this.accService = accService;

		get("/account/:id", (req, res) -> {
			long id = Long.parseLong(req.params("id"));
			return accService.get(id);
		});
	}
}
