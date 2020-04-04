package com.asu.secureBankApp.service;

import java.util.HashMap;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.dao.AccountRequestDAO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public interface NewAccountRequestService {

	HashMap<String, Object> getList(Authentication authentication);
	
	HashMap<String, Object> getApproval(AccountRequestDAO accountRequest, Authentication authentication);
	
	HashMap<String, Object> decline(AccountRequestDAO accountRequest, Authentication authentication);
	
	AccountRequestDAO getAccountRequestByReqId(Long reqId);
}
