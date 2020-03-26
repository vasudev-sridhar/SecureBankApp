package com.asu.secureBankApp.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.AccountResponses;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.asu.secureBankApp.dao.UserDAO;

import constants.ErrorCodes;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public StatusResponse updateInterest(@Valid UpdateInterestRequest updateInterestRequest) {
		StatusResponse response = new StatusResponse();
		AccountDAO account = accountRepository.findById(updateInterestRequest.getAccountNo()).orElse(null);
		if (account == null) {
			response.setIsSuccess(false);
			response.setMsg(ErrorCodes.ID_NOT_FOUND);
			return response;
		}
		boolean approvalRequired = false;
		if (approvalRequired) {
			// Submit request
		} else {
			double interest = account.getInterest();
			interest += updateInterestRequest.getInterest();
			account.setInterest(interest);
			accountRepository.save(account);
			response.setIsSuccess(true);
			response.setMsg(ErrorCodes.SUCCESS);
		}
		return response;
	}

	@Override
	@Transactional
	public StatusResponse createAccount(CreateAccountReqDAO createAccountReqDAO) {
		StatusResponse response = new StatusResponse();
		String userName = createAccountReqDAO.getUserName();
		AccountDAO account = new AccountDAO();
		UserDAO user = userRepository.findByUsername(userName);
		if (user == null) {
			response.setIsSuccess(false);
			response.setMsg(ErrorCodes.ID_NOT_FOUND);
			return response;
		}
		account.setUser(user);
		account.setBalance(100.0);
		account.setAccountType(createAccountReqDAO.getAccountType());
		account.setInterest(0.0);

		accountRepository.save(account);

		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);

		return response;
	}

	@Override
	public AccountResponses getAccounts(String userId) {
		AccountResponses response = new AccountResponses();
		List<AccountDAO> accounts = accountRepository.findByUserId(Integer.valueOf(userId));
		for(AccountDAO account : accounts) {
			account.setUser(null);
		}
		response.setAccounts(accounts);
		return response;
	}

}
