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
import javax.validation.constraints.NotNull;

import constants.TransactionStatus;
import constants.TransactionType;

@Entity(name="transaction")
public class TransactionDAO {
	
    @Id
    @Column(name="transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @Column(name="transaction_amount")
    private float transactionAmount;

    @Column(name="transaction_timestamp")
    private Date transactionTimestamp;

    @Column(name="type")
    private TransactionType type;
    
    @Column(name="status")
    private TransactionStatus status;

    @JoinColumn(name = "created_by", nullable = false, foreignKey = @ForeignKey(name="FK_TRANSACTION_USER_CREATED"))
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private UserDAO createdBy;
    
    @JoinColumn(name = "approved_by", nullable = false, foreignKey = @ForeignKey(name="FK_TRANSACTION_USER_APPROVED"))
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private UserDAO approvedBy;
    
    @Column(name="approved_at")
    private Date approvedAt;
    
    @NotNull(message="Account number cannot be empty")
    @JoinColumn(name = "from_account", nullable = false, foreignKey = @ForeignKey(name="FK_TRANSACTION_ACCOUNT_FROM"))
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private AccountDAO fromAccount;

    @JoinColumn(name = "to_account", nullable = false, foreignKey = @ForeignKey(name="FK_TRANSACTION_ACCOUNT_TO"))
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private AccountDAO toAccount;

    public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public UserDAO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDAO createdBy) {
		this.createdBy = createdBy;
	}

	public UserDAO getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(UserDAO approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getTransactionTimestamp() {
		return transactionTimestamp;
	}

	public void setTransactionTimestamp(Date transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}

	public Date getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(Date approvedAt) {
		this.approvedAt = approvedAt;
	}

	public AccountDAO getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(AccountDAO fromAccount) {
		this.fromAccount = fromAccount;
	}

	public AccountDAO getToAccount() {
		return toAccount;
	}

	public void setToAccount(AccountDAO toAccount) {
		this.toAccount = toAccount;
	}
    
}
