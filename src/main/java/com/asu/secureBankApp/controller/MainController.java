package com.asu.secureBankApp.controller;

import java.util.List;

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

import com.asu.secureBankApp.Repository.UserProfileRepository;
import com.asu.secureBankApp.Request.LoginRequest;
import com.asu.secureBankApp.Response.LoginResponse;
import com.asu.secureBankApp.dao.UserProfileDAO;

@Controller
@RequestMapping("/api")
public class MainController {

	@Autowired
	private UserProfileRepository userRepository;

	@GetMapping("/hello")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@PostMapping(value = "/login", consumes = { "application/json" })
	public @ResponseBody LoginResponse login(@RequestBody @Valid LoginRequest loginRequest, Model model) {
		LoginResponse response = new LoginResponse();
		response.setIsSuccess(false);
		UserProfileDAO dao = userRepository.findByUsernameAndPassword(loginRequest.getUsername(),
				loginRequest.getPassword());
		if (dao == null)
			return response;
		response.setIsSuccess(true);
		return response;

	}
}
