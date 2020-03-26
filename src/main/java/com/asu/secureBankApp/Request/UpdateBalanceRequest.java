package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class UpdateBalanceRequest {

	@NotNull
	private Integer accountNo;
	
	@NotNull
	private Float amount;
	
	public Integer getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
}
