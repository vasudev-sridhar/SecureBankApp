package com.asu.secureBankApp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.TransactionDAO;

public interface TransactionService {
	
	StatusResponse transfer(TransferRequest transferReq, Authentication auth, boolean isApproved);
	
	StatusResponse updateBalance(UpdateBalanceRequest updateBalanceRequest, Authentication auth, boolean isApproved) throws Exception;
	
	boolean doUpdateBalance(UpdateBalanceRequest updateBalanceRequest);
	
	StatusResponse approveTransaction(String transactionId, Authentication auth) throws Exception;
	
	StatusResponse rejectTransaction(String transactionId, Authentication auth);
	
	StatusResponse submitTransactionToHyperledger(TransactionDAO transactionDAO);
	
	List<TransactionDAO> getTransaction(Integer type, Integer status, String userName, Boolean isCritical, Authentication auth) throws Exception;
	
	ResponseEntity<InputStreamResource> downloadStatement(String userName, Authentication auth) throws IOException, Exception;
}
