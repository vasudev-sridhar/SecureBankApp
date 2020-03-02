package com.asu.secureBankApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asu.secureBankApp.Request.LoginRequest;
import com.asu.secureBankApp.Request.LogoutRequest;
import com.asu.secureBankApp.Response.LoginResponse;
import com.asu.secureBankApp.service.LoginService;

@Controller
@RequestMapping("/api")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping(value = "/login", consumes = { "application/json" })
	public @ResponseBody LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
		return loginService.login(loginRequest);
	}
	
	@PostMapping(value = "/logout", consumes = { "application/json" })
	public @ResponseBody LoginResponse logout(@RequestBody @Valid LogoutRequest logoutRequest) {
		return loginService.logout(logoutRequest);
	}
}
