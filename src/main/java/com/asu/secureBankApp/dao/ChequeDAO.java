package com.asu.secureBankApp.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity(name = "cheque")
public class ChequeDAO {

    @Id
    @Column(name = "cheque_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long checkId;

    @JsonIgnore
    @JoinColumn(name = "from_account", nullable = false, foreignKey = @ForeignKey(name="FK_CHEQUE_ACCOUNT_FROM"))
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDAO fromAccount;

    @JsonIgnore
    @JoinColumn(name = "to_account", nullable = false, foreignKey = @ForeignKey(name="FK_CHEQUE_TRANSACTION_ACCOUNT_TO"))
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDAO toAccount;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "issued_at")
    private Date issuedAt;

    @Column
    private int status;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
