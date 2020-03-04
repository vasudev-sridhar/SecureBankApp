package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class UpdateBalanceRequest {

	@NotNull
	private Integer accountNo;
	
	@NotNull
	private Double amount;
	
	public Integer getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
