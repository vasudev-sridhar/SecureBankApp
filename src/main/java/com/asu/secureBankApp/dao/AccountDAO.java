package com.asu.secureBankApp.dao;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "account")
public class AccountDAO {

	@Id
	@Column(name = "account_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name="FK_ACCOUNT_USER"))
	@ManyToOne(fetch = FetchType.EAGER)
	private UserDAO user;
	
	private Double balance;
	
	@Column(name = "account_type")
	private int accountType;
	
	private Double interest;
	
	private Date created;
	
	private Date updated;
	
	@Column(name = "routing_no")
	private int routingNo;

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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getRoutingNo() {
		return routingNo;
	}

	public void setRoutingNo(int routingNo) {
		this.routingNo = routingNo;
	}
	
}
