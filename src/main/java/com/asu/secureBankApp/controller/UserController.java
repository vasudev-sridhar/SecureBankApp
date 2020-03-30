package com.asu.secureBankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asu.secureBankApp.dao.UserDAO;
import com.asu.secureBankApp.service.UserService;

@Controller
@RequestMapping(value="/api/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping(value = "/get/{userId}")
	public @ResponseBody UserDAO getUser(@PathVariable Integer userId, Authentication auth) {
		return userService.getUser(userId, auth);
	}
	
	@GetMapping(value = "/get")
	public @ResponseBody List<UserDAO> getAllUsers(Authentication auth) throws Exception {
		return userService.getAllUsers(auth);
	}
}
