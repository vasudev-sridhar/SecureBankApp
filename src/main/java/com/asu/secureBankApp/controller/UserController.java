package com.asu.secureBankApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Request.AppointmentRequest;
import com.asu.secureBankApp.Request.UserDOBRequest;
import com.asu.secureBankApp.Request.UserRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.UserDAO;
import com.asu.secureBankApp.service.UserService;

@RestController
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
	
	@PostMapping(value = "/updateEmail")
	public StatusResponse updateEmail(@RequestBody @Valid UserRequest user) {
		return userService.updateEmail(user);
	}
	@PostMapping(value = "/updateAddress")
	public StatusResponse updateAddress(@RequestBody @Valid UserRequest user) {
		return userService.updateAddress(user);
	}
	@PostMapping(value = "/updatePhone")
	public StatusResponse updatePhone(@RequestBody @Valid UserRequest user) {
		return userService.updatePhone(user);
	}
	@PostMapping(value = "/updateDOB")
	public StatusResponse updateDOB(@RequestBody @Valid UserDOBRequest user) {
		return userService.updateDOB(user);
	}

}
