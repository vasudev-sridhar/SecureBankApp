package com.asu.secureBankApp.service;

import java.util.HashMap;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface NewAccountRequestService {

	HashMap<String, Object> getList(int page, Authentication authentication);
	
}
