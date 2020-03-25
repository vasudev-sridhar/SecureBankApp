package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BankUserServiceImpl {

    @Autowired
    UserRepository userRepository;

    List<AccountDAO> getAccountsByEmail(String emailId) {
        List<AccountDAO> accountsByEmail = new ArrayList<>() ;
        if(null != emailId) {
            UserDAO user = userRepository.findByEmailId(emailId);
            accountsByEmail = user.getAccounts();
        }
        return accountsByEmail;
    }

    List<AccountDAO> getAccountsByContact(String phoneNo) {
        List<AccountDAO> accountsByContact = new ArrayList<>() ;
        if(null != phoneNo) {
            UserDAO user = userRepository.findByContact(phoneNo);
            accountsByContact = user.getAccounts();
        }
        return accountsByContact;
    }

}
