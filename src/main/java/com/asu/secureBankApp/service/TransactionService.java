package com.asu.secureBankApp.service;

import com.asu.secureBankApp.dao.TransactionDAO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TransactionService {

    List<TransactionDAO> getListOfTransactions(String emailId);

}
