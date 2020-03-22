package com.asu.secureBankApp.Response;

import java.util.List;

import com.asu.secureBankApp.dao.AccountDAO;

public class AccountResponses {

	private List<AccountDAO> accounts;

	public List<AccountDAO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDAO> accounts) {
		this.accounts = accounts;
	}
}
