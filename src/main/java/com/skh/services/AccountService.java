package com.skh.services;

import java.math.BigDecimal;

import com.skh.model.Account;

public interface AccountService {
	
	/**
	 * Creates an account
	 * @param currency - 3 char coded currency. e.g MNT, USD
	 * @param balance - starting balance
	 * @return
	 */
	Account create(String currency, BigDecimal balance);

	/**
	 * Retrieves an account
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	Account get(long id) throws Exception;
}
