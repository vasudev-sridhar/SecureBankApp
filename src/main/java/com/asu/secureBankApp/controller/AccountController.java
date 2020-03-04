package com.asu.secureBankApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.service.AccountService;

@Controller
@RequestMapping("/api")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@PostMapping(value = "/balance", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateBalance(@RequestBody @Valid UpdateBalanceRequest updateBalanceRequest) {
		return accountService.updateBalance(updateBalanceRequest);
	}
}
