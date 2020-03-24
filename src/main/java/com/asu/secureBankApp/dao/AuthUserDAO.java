package com.asu.secureBankApp.dao;

import java.sql.Timestamp;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name="FK_auth_user_user"))
	private UserDAO user;
	
    @Column(name = "email")
    private String email;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private Integer otp;
	
	private Timestamp expiry;

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

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Timestamp getExpiry() {
		return expiry;
	}

	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
