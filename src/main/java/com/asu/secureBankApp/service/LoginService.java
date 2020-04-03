package com.asu.secureBankApp.service;

import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.Request.LoginRequest;
import com.asu.secureBankApp.Request.LogoutRequest;
import com.asu.secureBankApp.Response.LoginResponse;

public interface LoginService {
	
	LoginResponse login(LoginRequest loginRequest, Authentication auth);
	
	LoginResponse logout(LogoutRequest logoutRequest);
}
