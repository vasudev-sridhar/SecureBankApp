package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class UpdateInterestRequest {

    @NotNull
    private Integer accountNo;

    @NotNull
    private Double interest;

    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double amount) {
        this.interest = amount;
    }

}
