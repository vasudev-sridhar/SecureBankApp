package com.asu.secureBankApp.controller;

import com.asu.secureBankApp.dao.TransactionDAO;
import com.asu.secureBankApp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/statement/list")
    public List<TransactionDAO> getListOfTransactions(Authentication authentication) {
        return transactionService.getListOfTransactions(authentication.getName());
    }

    @GetMapping(value = "/statements")
    public HashMap<String, Object> getStatements() {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("modelAndView", "statement_list");
        return responseMap;
    }

}
