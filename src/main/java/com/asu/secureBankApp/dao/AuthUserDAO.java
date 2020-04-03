package com.asu.secureBankApp.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import constants.Status;

@Entity(name = "auth_user")
public class AuthUserDAO {

	@Id
	@Column(name = "auth_user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name="FK_auth_user_user"))
	private UserDAO user;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private Date expiry;
	
	private String otp;
	private String r;

	public int getOtp() {
		return Integer.parseInt(otp);
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getr() {
		return r;
	}

	public void setr(String r) {
		this.r = r;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public UserDAO getUser() {
		return user;
	}

	public void setUser(UserDAO user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	
}
