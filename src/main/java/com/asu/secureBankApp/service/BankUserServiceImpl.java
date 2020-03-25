package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BankUserServiceImpl implements BankUserService {

    @Autowired
    UserRepository userRepository;

    public List<AccountDAO> getAccountsByEmail(String emailId) {
        List<AccountDAO> accountsByEmail = new ArrayList<>() ;
        if(null != emailId) {
            UserDAO user = userRepository.findByEmailId(emailId);
            accountsByEmail = user.getAccounts();
        }
        return accountsByEmail;
    }

    public List<AccountDAO> getAccountsByContact(String phoneNo) {
        List<AccountDAO> accountsByContact = new ArrayList<>() ;
        if(null != phoneNo) {
            UserDAO user = userRepository.findByContact(phoneNo);
            accountsByContact = user.getAccounts();
        }
        return accountsByContact;
    }
    
    @Override
	public UserDAO getUserByEmail(String emailId) {
		UserDAO user = userRepository.findByEmailId(emailId);
		return user;
	}

}
