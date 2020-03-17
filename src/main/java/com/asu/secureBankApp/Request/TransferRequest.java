package com.asu.secureBankApp.Request;

public class TransferRequest {

	private int fromAccNo;
	private int toAccNo;
	private Double transferAmount;
	
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
	public Double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
}
