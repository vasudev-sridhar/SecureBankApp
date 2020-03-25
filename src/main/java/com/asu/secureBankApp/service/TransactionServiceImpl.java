package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Repository.TransactionRepository;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.TransactionDAO;
import com.asu.secureBankApp.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    BankUserService bankUserService;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<TransactionDAO> getListOfTransactions(String emailId) {
        int userId;
        List<TransactionDAO> listOfTransactions= new ArrayList<>();
        List<AccountDAO> listOfAccounts = bankUserService.getAccountsByEmail(emailId);
        for(AccountDAO account: listOfAccounts) {
            listOfTransactions.addAll(transactionRepository.findAllByAccountNo(account.getId()));
        }
        return null;
    }
}
