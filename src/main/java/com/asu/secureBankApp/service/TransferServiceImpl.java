package com.asu.secureBankApp.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.dao.AccountDAO;

@Service
public class TransferServiceImpl implements TransferService{
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Transactional
	public String transfer(TransferRequest transferReq) {
		UpdateBalanceRequest fromUpdateBalanceRequest = new UpdateBalanceRequest();
		UpdateBalanceRequest toUpdateBalanceRequest = new UpdateBalanceRequest();
		
		AccountDAO fromAccount = accountRepository.findById(transferReq.getFromAccNo()).orElse(null);
		AccountDAO toAccount = accountRepository.findById(transferReq.getToAccNo()).orElse(null);
	
		fromUpdateBalanceRequest.setAccountNo(fromAccount.getId());
		fromUpdateBalanceRequest.setAmount(-transferReq.getTransferAmount());
		accountService.updateBalance(fromUpdateBalanceRequest);
		
		toUpdateBalanceRequest.setAccountNo(toAccount.getId());
		toUpdateBalanceRequest.setAmount(transferReq.getTransferAmount());
		accountService.updateBalance(toUpdateBalanceRequest);
		
		return "success";
	}
	

	

}
