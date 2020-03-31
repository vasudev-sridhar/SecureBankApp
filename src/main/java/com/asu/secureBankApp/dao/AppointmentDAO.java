package com.asu.secureBankApp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity(name = "appointment")
public class AppointmentDAO {
	
	@Id
	@Column(name = "appointment_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer appointment_id;
	
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name="FK_Appointment"))
	@ManyToOne(fetch = FetchType.EAGER)
	private UserDAO user;
	
	@NotNull
	private Date app_date;
	
	@NotNull
	private String app_time;

	public Integer getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}

	public UserDAO getUser() {
		return user;
	}

	public void setUser(UserDAO user) {
		this.user = user;
	}

	public Date getApp_date() {
		return app_date;
	}

	public void setApp_date(Date app_date) {
		this.app_date = app_date;
	}

	public String getApp_time() {
		return app_time;
	}

	public void setApp_time(String app_time) {
		this.app_time = app_time;
	}

}
