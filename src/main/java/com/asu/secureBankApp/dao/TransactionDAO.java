package com.asu.secureBankApp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "Transaction")
public class TransactionDAO {
	@Id
	@Column(name = "transaction_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "transaction_amt")
	private int transaction_amt;
	
    @NotNull(message="Account number cannot be empty")
    @Column(name="account_no")
    private int account_no;

    @Column(name="balance")
    private float balance;
    
    @Column(name="transaction_type")
    private float transaction_type;

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

	public int getTransaction_amt() {
		return transaction_amt;
	}

	public void setTransaction_amt(int transaction_amt) {
		this.transaction_amt = transaction_amt;
	}

	public int getAccount_no() {
		return account_no;
	}

	public void setAccount_no(int account_no) {
		this.account_no = account_no;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
    
}
