package com.asu.secureBankApp.service;

import org.springframework.security.core.Authentication;

import com.asu.secureBankApp.dao.UserDAO;

public interface UserService {

	UserDAO getUser(Integer userId, Authentication auth);
}
