package com.asu.secureBankApp.Response;

import org.springframework.web.bind.annotation.ResponseBody;


public class LoginResponse {

	Boolean isSuccess;

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
}
