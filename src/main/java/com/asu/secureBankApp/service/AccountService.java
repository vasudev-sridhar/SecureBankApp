package com.asu.secureBankApp.service;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.AccountResponses;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.asu.secureBankApp.dao.AccountDAO;;

public interface AccountService {

	StatusResponse updateBalance(UpdateBalanceRequest updateBalanceRequest);

	StatusResponse createAccount(CreateAccountReqDAO createAccountReqDAO);

	StatusResponse updateInterest(UpdateInterestRequest updateInterestRequest);
	
	AccountResponses getAccounts(String userId);
	
	HashMap<String, Object> getListPage(Authentication authentication, int page);
}
