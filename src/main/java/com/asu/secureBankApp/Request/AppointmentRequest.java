package com.asu.secureBankApp.Request;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class AppointmentRequest {
	
	@NotNull
	private Integer userid;
	
	@NotNull
	private Date date;
	
	@NotNull
	private String time;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
		
}
