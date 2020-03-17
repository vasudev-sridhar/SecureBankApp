package com.asu.secureBankApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.service.TransferService;

@RestController
@RequestMapping("/api")
public class TransferController {
	
	@Autowired
	private TransferService transferService;

	
	@PostMapping(value = "/transfer", consumes = { "application/json" })
	public String transfer(@RequestBody @Valid TransferRequest transferReq) {
		
		String response = transferService.transfer(transferReq);
		
		return response;
	}
		
	
}
