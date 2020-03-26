package com.asu.secureBankApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping(value = "/transfer", consumes = { "application/json" })
	public StatusResponse transfer(@RequestBody @Valid TransferRequest transferReq, Authentication auth) {
		return transactionService.transfer(transferReq, auth, false);
	}
		
	@PostMapping(value = "/balance", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateBalance(@RequestBody @Valid UpdateBalanceRequest updateBalanceRequest, Authentication auth) {
		return transactionService.updateBalance(updateBalanceRequest, auth, false);
	}
	
	@PostMapping(value = "/approve/{transaction_id}", consumes = { "application/json" })
	public @ResponseBody StatusResponse approveTransaction(@PathVariable(value = "transaction_id") String transactionId, Authentication auth) {
		return transactionService.approveTransaction(transactionId, auth);
	}
}
