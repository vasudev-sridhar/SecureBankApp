package com.asu.secureBankApp.dao;

public class CreateAccountReqDAO {

	private Integer accountType;
	private String userName;
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
