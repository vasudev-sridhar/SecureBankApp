package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.ChequeRepository;
import com.asu.secureBankApp.Request.ChequeRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.ChequeDAO;
import constants.Status;
import com.asu.secureBankApp.Config.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ChequeServiceImpl implements ChequeService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ChequeRepository chequeRepository;

    @Override
    public StatusResponse issueCheque(ChequeRequest chequeRequest) {
        ChequeDAO chequeToIssue = new ChequeDAO();
        StatusResponse response = new StatusResponse();
//        AccountDAO fromAccount = accountRepository.findByAccountNo(chequeRequest.getFromAccNo());
//        AccountDAO toAccount = accountRepository.findByAccountNo(chequeRequest.getToAccNo());
        Float amount = chequeRequest.getTransferAmount();
        chequeToIssue.setFromAccount(chequeRequest.getFromAccNo());
        chequeToIssue.setToAccount(chequeRequest.getToAccNo());
        Optional<AccountDAO> toAccount = accountRepository.findById(chequeRequest.getToAccNo());
        Optional<AccountDAO> fromAccount = accountRepository.findById(chequeRequest.getFromAccNo());
        chequeToIssue.setAmount(amount);
        if(null==toAccount){
            response.setIsSuccess(false);
            response.setMsg("ToAccount does not exist");
            return response;
        }
        if(fromAccount.get().getBalance() < amount){
            response.setIsSuccess(false);
            response.setMsg("Insufficient funds");
            return response;
        }
        chequeToIssue.setStatus(Constants.CHEQUE_ISSUE_PENDING);
        chequeRepository.save(chequeToIssue);
        return null;
    }
}
