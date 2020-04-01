package com.asu.secureBankApp.controller;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.AccountResponses;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.asu.secureBankApp.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	SecureRandom randomInt = new SecureRandom();

	@GetMapping(value = "/get/{user_id}")
	public @ResponseBody AccountResponses getAccounts(@PathVariable(value = "user_id") String userId, Authentication auth) {
		AccountResponses response = new AccountResponses();
		System.out.println("Auth1:" + auth);
		System.out.println("Auth: " + auth.getName() + " " + auth.getAuthorities().size());
		List<AccountDAO> accounts = accountRepository.findByUserId(Integer.parseInt(userId));
		for(AccountDAO account : accounts) {
			account.setUser(null);
		}
		response.setAccounts(accounts);
		return response;
	}

	@PostMapping(value = "/createAccount", consumes = { "application/json" })
	public @ResponseBody StatusResponse createNewAccount(@RequestBody @Valid CreateAccountReqDAO createAccountReqDAO) {
		StatusResponse response = accountService.createAccount(createAccountReqDAO);
		return response;
	}

	@PostMapping(value = "/newAccount", consumes =  {"application/json"})
	public @ResponseBody HashMap<String, String> startNewAccount (@RequestBody AccountDAO account, Authentication authentication) throws JsonProcessingException {
		return accountService.createNewAccount(account, authentication);
	}

	@PatchMapping(value = "/updateInterest", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateInterest(
			@RequestBody @Valid UpdateInterestRequest updateInterestRequest) {
		return accountService.updateInterest(updateInterestRequest);
	}
}
