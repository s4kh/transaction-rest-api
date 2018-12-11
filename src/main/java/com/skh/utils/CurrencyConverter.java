package com.skh.utils;

import java.math.BigDecimal;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CurrencyConverter {
	private static final String API_KEY = "b7e5dfb6cef346b36f339d59f2cd8d78";

	public static BigDecimal convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
		BigDecimal convertedAmt = new BigDecimal(-1);
		try {
			HttpResponse<String> response = Unirest.post("http://data.fixer.io/api/latest")
			    .header("accept", "application/json").queryString("access_key", API_KEY).queryString("from", fromCurrency)
			    .queryString("to", toCurrency).queryString("amount", amount).asString();
			System.out.println(response.getBody());
			UnirestResponse res = new Gson().fromJson(response.getBody(), UnirestResponse.class);
			convertedAmt = res.getResult();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return convertedAmt;
	}

	public static void main(String[] args) {
		System.out.println(convertCurrency("MNT", "USD", new BigDecimal(5000)));
	}
}
