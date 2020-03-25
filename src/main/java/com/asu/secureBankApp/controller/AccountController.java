package com.asu.secureBankApp.controller;

import java.util.List;

import javax.validation.Valid;

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
@RequestMapping("/api//account")
public class AccountController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	@GetMapping(value = "/get/{user_id}")
	public @ResponseBody AccountResponses getAccounts(@PathVariable(value = "user_id") String userId, Authentication auth) {
		AccountResponses response = new AccountResponses();
		System.out.println("Auth1:" + auth);
		System.out.println("Auth: " + auth.getName() + " " + auth.getAuthorities().size());
		List<AccountDAO> accounts = accountRepository.findByUserId(Integer.valueOf(userId));
		for(AccountDAO account : accounts) {
			account.setUser(null);
		}
		response.setAccounts(accounts);
		return response;
	}
	
	@PostMapping(value = "/balance", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateBalance(@RequestBody @Valid UpdateBalanceRequest updateBalanceRequest) {
		return accountService.updateBalance(updateBalanceRequest);
	}

	@PostMapping(value = "/createAccount", consumes = { "application/json" })
	public @ResponseBody StatusResponse createNewAccount(@RequestBody @Valid CreateAccountReqDAO createAccountReqDAO) {
		StatusResponse response = accountService.createAccount(createAccountReqDAO);
		return response;
	}

	@PatchMapping(value = "/updateInterest", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateInterest(
			@RequestBody @Valid UpdateInterestRequest updateInterestRequest) {
		return accountService.updateInterest(updateInterestRequest);
	}
}
