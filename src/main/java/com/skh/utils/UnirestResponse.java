package com.skh.utils;

import java.math.BigDecimal;

public class UnirestResponse {

	private boolean success;
	private BigDecimal result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}

}
