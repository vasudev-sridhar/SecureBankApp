package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;

public interface AccountService {

	StatusResponse updateBalance(UpdateBalanceRequest updateBalanceRequest);

	StatusResponse saveAccount(AccountDAO account);

	StatusResponse updateInterest(UpdateInterestRequest updateInterestRequest);

}
