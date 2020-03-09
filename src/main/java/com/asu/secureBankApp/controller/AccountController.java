package com.asu.secureBankApp.controller;

import javax.validation.Valid;

import com.asu.secureBankApp.Request.UpdateInterestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.asu.secureBankApp.dao.UserDAO;
import com.asu.secureBankApp.service.AccountService;

@Controller
@RequestMapping("/api")
public class AccountController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	AccountService accountService;

	@PostMapping(value = "/balance", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateBalance(@RequestBody @Valid UpdateBalanceRequest updateBalanceRequest) {
		return accountService.updateBalance(updateBalanceRequest);
	}

	@PostMapping(value = "/create", consumes = { "application/json" })
	public @ResponseBody String createNewAccount(@RequestBody @Valid CreateAccountReqDAO createAccountReqDAO) {
		String userName = createAccountReqDAO.getUserName();
		String response = "fail";
		AccountDAO account = new AccountDAO();
		UserDAO user = userRepository.findByUsername(userName);
		if (user == null)
			return response;
		account.setUser(user);
		account.setBalance(100.0);
		account.setAccountType(createAccountReqDAO.getAccountType());
		account.setInterest(0.0);
		StatusResponse status = accountService.saveAccount(account);
		response = "success";
		return response;

	}

	@PatchMapping(value = "/updateInterest", consumes = { "application/json"})
	public @ResponseBody StatusResponse updateInterest(@RequestBody @Valid UpdateInterestRequest updateInterestRequest) {
		return accountService.updateInterest(updateInterestRequest);
	}
}
