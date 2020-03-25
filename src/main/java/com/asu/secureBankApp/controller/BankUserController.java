package com.asu.secureBankApp.controller;

import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankUserController {

    @Autowired
    BankUserService bankUserService;

    @RequestMapping(value = "/accounts/email/{emailId}", method= RequestMethod.GET)
    public List<AccountDAO> getAccountsByEmail(@PathVariable String emailId) {
        return bankUserService.getAccountsByEmail(emailId);
    }

    @RequestMapping(value = "/accounts/contact/{phoneNo}", method= RequestMethod.GET)
    public List<AccountDAO> getAccountsByContact(@PathVariable String phoneNo){
        return bankUserService.getAccountsByContact(phoneNo);
    }

}
