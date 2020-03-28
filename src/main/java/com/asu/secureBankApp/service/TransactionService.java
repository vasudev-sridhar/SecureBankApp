package com.asu.secureBankApp.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.TransactionDAO;

public interface TransactionService {
	
	StatusResponse transfer(TransferRequest transferReq, Authentication auth, boolean isApproved);
	
	StatusResponse updateBalance(UpdateBalanceRequest updateBalanceRequest, Authentication auth, boolean isTransfer);
	
	boolean doUpdateBalance(UpdateBalanceRequest updateBalanceRequest);
	
	StatusResponse approveTransaction(String transactionId, Authentication auth);
	
	StatusResponse rejectTransaction(String transactionId, Authentication auth);
	
	StatusResponse submitTransactionRequest(TransactionDAO transactionDAO);
	
	List<TransactionDAO> getTransaction(Integer type, Integer status, String userName, Authentication auth) throws Exception;
}
