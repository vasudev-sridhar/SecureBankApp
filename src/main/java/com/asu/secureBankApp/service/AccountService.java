package com.asu.secureBankApp.service;

import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.AccountResponses;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

public interface AccountService {

	StatusResponse createAccount(CreateAccountReqDAO createAccountReqDAO);

	StatusResponse updateInterest(UpdateInterestRequest updateInterestRequest);
	
	AccountResponses getAccounts(String userId);
	
	AccountResponses getAllAccounts();

	String accountToString(Map<String, Object> accountMap) throws JsonProcessingException;

	Map<String, Object> stringToAccount(String accountString) throws JsonProcessingException;

	HashMap<String, String> createNewAccount(AccountDAO account, Authentication authentication) throws JsonProcessingException;
	
	AccountDAO saveOrUpdate(AccountDAO account);
	
}
