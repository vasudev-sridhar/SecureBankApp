package com.asu.secureBankApp.service;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.AccountResponses;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.asu.secureBankApp.dao.Transaction;
import com.asu.secureBankApp.dao.AccountDAO;;

public interface AccountService {

	StatusResponse updateBalance(UpdateBalanceRequest updateBalanceRequest);

	StatusResponse createAccount(CreateAccountReqDAO createAccountReqDAO);

	StatusResponse updateInterest(UpdateInterestRequest updateInterestRequest);
	
	AccountResponses getAccounts(String userId);
	
	AccountDAO getAccountByAccountNo(Integer accNo);
	
	HashMap<String, Object> getListPage(Authentication authentication, int page);
	
	HashMap<String, Object> depositPost(Transaction transaction, BindingResult bindingResult,Authentication authentication,  RedirectAttributes redirectAttributes);
	
	AccountDAO saveOrUpdate(AccountDAO account);
	
	HashMap<String, Object> depositTier1(String message);

	HashMap<String, Object> depositUser(String message, Authentication authentication);
}
