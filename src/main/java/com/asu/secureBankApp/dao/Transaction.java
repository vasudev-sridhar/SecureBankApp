package com.asu.secureBankApp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "Transaction")
public class Transaction {
	@Id
	@Column(name = "transaction_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "transaction_amt")
	private Double transaction_amt;
	
    @NotNull(message="Account number cannot be empty")
    @Column(name="account_no")
    private Integer account_no;

    @Column(name="balance")
    private Double balance;
    
    @Column(name="transaction_type")
    private float transaction_type;
    
    @Column(name="status")
    private Integer status;

	public float getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(float transaction_type) {
		this.transaction_type = transaction_type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getTransaction_amt() {
		return transaction_amt;
	}

	public void setTransaction_amt(Double transaction_amt) {
		this.transaction_amt = transaction_amt;
	}

	public Integer getAccount_no() {
		return account_no;
	}

	public void setAccount_no(Integer account_no) {
		this.account_no = account_no;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}
