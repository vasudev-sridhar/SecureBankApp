package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class LogoutRequest {

	@NotNull
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
