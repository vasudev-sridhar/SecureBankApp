package com.asu.secureBankApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.Request.AppointmentRequest;
import com.asu.secureBankApp.Request.UserDOBRequest;
import com.asu.secureBankApp.Request.UserRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AuthUserDAO;
import com.asu.secureBankApp.dao.UserDAO;

public interface UserService {

	UserDAO getUser(Integer userId, Authentication auth);

	List<UserDAO> getAllUsers(Authentication auth) throws Exception;
	
	StatusResponse updateEmail(UserRequest userReq);
	StatusResponse updateAddress(UserRequest userReq);
	StatusResponse updatePhone(UserRequest userReq);
	StatusResponse updateDOB(UserDOBRequest userReq);

	String checkIfUsernameExist(String username);
	StatusResponse signup(UserDAO newUser);
	
	public Optional<AuthUserDAO> findByEmail(String email);

}