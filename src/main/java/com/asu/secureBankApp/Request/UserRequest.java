package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class UserRequest {
	
	@NotNull
	private Integer userid;
	
	@NotNull
	private String newInfo;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getNewInfo() {
		return newInfo;
	}

	public void setNewInfo(String newInfo) {
		this.newInfo = newInfo;
	}

}
