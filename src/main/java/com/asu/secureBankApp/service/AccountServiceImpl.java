package com.asu.secureBankApp.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;

import constants.ErrorCodes;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	@Transactional
	public StatusResponse updateBalance(@Valid UpdateBalanceRequest updateBalanceRequest) {
		StatusResponse response = new StatusResponse();
		AccountDAO account = accountRepository.findById(updateBalanceRequest.getAccountNo()).orElse(null);
		if (account == null) {
			response.setIsSuccess(false);
			response.setMsg(ErrorCodes.ID_NOT_FOUND);
			return response;
		}
		boolean approvalRequired = false;
		if (approvalRequired) {
			// Submit request
		} else {
			double bal = account.getBalance();
			bal += updateBalanceRequest.getAmount();
			account.setBalance(bal);
			accountRepository.save(account);

			response.setIsSuccess(true);
			response.setMsg(ErrorCodes.SUCCESS);
		}
		return response;
	}

	@Override
	public StatusResponse saveAccount(AccountDAO account) {

		StatusResponse response = new StatusResponse();
		if (account == null) {
			response.setIsSuccess(false);
			response.setMsg(ErrorCodes.ID_NOT_FOUND);
			return response;
		}

		accountRepository.save(account);

		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);

		return response;
	}

}
