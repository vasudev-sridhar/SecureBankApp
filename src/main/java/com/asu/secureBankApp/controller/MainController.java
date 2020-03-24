package com.asu.secureBankApp.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asu.secureBankApp.Repository.AuthUserRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.LoginRequest;
import com.asu.secureBankApp.Response.LoginResponse;
import com.asu.secureBankApp.dao.AuthUserDAO;
import com.asu.secureBankApp.dao.UserDAO;

import constants.Status;

@Controller
@RequestMapping("/api")
public class MainController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthUserRepository authUserRepository;

	@GetMapping("/hello")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@PostMapping(value = "/login2", consumes = { "application/json" })
	public @ResponseBody LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
		LoginResponse response = new LoginResponse();
		response.setIsSuccess(false);
		UserDAO user = userRepository.findByUsernameAndPassword(loginRequest.getUsername(),
				loginRequest.getPassword());
		if (user == null)
			return response;
		System.out.println("\n\n\n" + user.getId());
		response.setIsSuccess(true);
		AuthUserDAO authUser = new AuthUserDAO();
		authUser.setUser(user);
		authUser.setStatus(Status.ACTIVE);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 10);
		authUser.setExpiry(new Timestamp(System.currentTimeMillis()));
		authUserRepository.save(authUser);
		return response;

	}
}
