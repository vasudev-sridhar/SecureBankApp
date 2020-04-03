package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class OtpRequest {

	@NotNull
	private int otpnumber;

	public int getOtpnumber() {
		return otpnumber;
	}

	public void setOtpnumber(int otpnumber) {
		this.otpnumber = otpnumber;
	}

}

