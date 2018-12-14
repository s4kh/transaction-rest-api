package com.skh.utils;

import static spark.Spark.after;
import static spark.Spark.exception;

import com.google.gson.Gson;
import com.skh.exceptions.ErrorResponse;

public class CommonFilter implements Filters {

	@Override
	public void filters() {
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
