package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class TransferRequest {

	@NotNull
	private int fromAccNo;
	private int toAccNo;
	
	@NotNull
	private Float transferAmount;
	
	public int getFromAccNo() {
		return fromAccNo;
	}
	public void setFromAccNo(int fromAccNo) {
		this.fromAccNo = fromAccNo;
	}
	public int getToAccNo() {
		return toAccNo;
	}
	public void setToAccNo(int toAccNo) {
		this.toAccNo = toAccNo;
	}
	public Float getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(Float transferAmount) {
		this.transferAmount = transferAmount;
	}
}
