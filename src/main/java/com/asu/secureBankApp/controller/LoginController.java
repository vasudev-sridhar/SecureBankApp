package com.asu.secureBankApp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Resource(name = "authenticationManager")
	private AuthenticationManager authManager;

	@PostMapping(value = "/login", consumes = { "application/json" })
	public @ResponseBody LoginResponse login(@RequestBody @Valid LoginRequest loginRequest, final HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authReq =
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
        return loginService.login(loginRequest);
	}

	@PostMapping(value = "/logout", consumes = { "application/json" })
	public @ResponseBody LoginResponse logout(@RequestBody @Valid LogoutRequest logoutRequest) {
		return loginService.logout(logoutRequest);
	}
}
