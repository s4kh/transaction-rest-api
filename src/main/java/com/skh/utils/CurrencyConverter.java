package com.skh.utils;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CurrencyConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverter.class);

	public static BigDecimal convert(String fromCurrency, String toCurrency, BigDecimal amount) {
		BigDecimal convertedAmt = new BigDecimal(-1);
		try {

			String from_to = String.format("%s_%s", fromCurrency, toCurrency);
			String url = String.format("http://free.currencyconverterapi.com/api/v6/convert?q=%s&compact=y", from_to);
			String response = HttpGetClient.sendGet(url);

			JsonObject jsonObj = new Gson().fromJson(response, JsonObject.class);
			BigDecimal rate = jsonObj.get(from_to).getAsJsonObject().get("val").getAsBigDecimal();

			LOGGER.info("{}/{}:{} {}", fromCurrency, toCurrency, rate, amount);
			convertedAmt = amount.multiply(rate);
			LOGGER.info("Converted amt:{}", convertedAmt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedAmt;
	}
}
