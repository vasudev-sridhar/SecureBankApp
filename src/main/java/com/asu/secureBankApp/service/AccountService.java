package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;

public interface AccountService {

	StatusResponse updateBalance(UpdateBalanceRequest updateBalanceRequest);

	StatusResponse createAccount(CreateAccountReqDAO createAccountReqDAO);

	StatusResponse updateInterest(UpdateInterestRequest updateInterestRequest);

}
