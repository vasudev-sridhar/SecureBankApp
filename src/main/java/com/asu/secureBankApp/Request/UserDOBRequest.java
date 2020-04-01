package com.asu.secureBankApp.Request;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class UserDOBRequest {
	
	@NotNull
	private Integer userid;
	
	@NotNull
	private Date newInfo;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getNewInfo() {
		return newInfo;
	}

	public void setNewInfo(Date newInfo) {
		this.newInfo = newInfo;
	}

	
}
