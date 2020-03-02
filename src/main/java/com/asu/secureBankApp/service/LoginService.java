package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.LoginRequest;
import com.asu.secureBankApp.Request.LogoutRequest;
import com.asu.secureBankApp.Response.LoginResponse;

public interface LoginService {
	
	public LoginResponse login(LoginRequest loginRequest);
	
	public LoginResponse logout(LogoutRequest logoutRequest);
}
