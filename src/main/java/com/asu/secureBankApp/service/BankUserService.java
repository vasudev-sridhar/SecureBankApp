package com.asu.secureBankApp.service;

import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankUserService {

    List<AccountDAO> getAccountsByEmail(String data);

    List<AccountDAO> getAccountsByContact(String phoneNo);
    
    UserDAO getUserByEmail(String email);

    UserDAO getUserByUserId(Long userId);

}
