package io.reflectoring.buckpal.adapter.in.web;

/**
 * Web-layer DTO for representing an account balance without exposing domain objects.
 */
public class AccountBalanceResponse {

	private final String balance;

	public AccountBalanceResponse(String balance) {
		this.balance = balance;
	}

	public String getBalance() {
		return balance;
	}

}
