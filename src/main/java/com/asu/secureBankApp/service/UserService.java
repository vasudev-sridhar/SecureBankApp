package com.asu.secureBankApp.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.dao.UserDAO;

public interface UserService {

	UserDAO getUser(Integer userId, Authentication auth);

	List<UserDAO> getAllUsers(Authentication auth) throws Exception;
}
