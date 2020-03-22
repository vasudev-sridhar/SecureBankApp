package com.asu.secureBankApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.asu.secureBankApp.Request.LogoutRequest;
import com.asu.secureBankApp.Response.LoginResponse;
import com.asu.secureBankApp.service.LoginService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	/*
	 * @PostMapping(value = "/login", consumes = { "application/json" })
	 * public @ResponseBody LoginResponse login(@RequestBody @Valid LoginRequest
	 * loginRequest) { System.out.println("In loginController"); return
	 * loginService.login(loginRequest); }
	 */
	
//	@GetMapping(value = "/login", consumes = { "application/json" })
//	public @ResponseBody LoginResponse login2() {
//		System.out.println("In loginController");
//		return loginService.login(new LoginRequest());
//	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		System.out.println("In loginController2");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login"); // resources/template/login.html
		return modelAndView;
	}
	
//	@GetMapping(value = "/login", consumes = { "application/json" })
//	public @ResponseBody LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
//		System.out.println("GET In loginController");
//		return loginService.login(loginRequest);
//	}
	
	@PostMapping(value = "/logout", consumes = { "application/json" })
	public @ResponseBody LoginResponse logout(@RequestBody @Valid LogoutRequest logoutRequest) {
		return loginService.logout(logoutRequest);
	}
}
