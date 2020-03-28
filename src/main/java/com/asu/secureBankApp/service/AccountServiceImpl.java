package com.asu.secureBankApp.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.asu.secureBankApp.Config.Constants;
import com.asu.secureBankApp.Repository.AccountRequestRepository;
import com.asu.secureBankApp.dao.AccountRequestDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.UserRepository;
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
	AccountRequestRepository accountRequestRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	BankUserService bankUserService;

	ObjectMapper objectMapper = new ObjectMapper();

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
		List<AccountDAO> accounts = accountRepository.findByUserId(Long.parseLong(userId));
		for(AccountDAO account : accounts) {
			account.setUser(null);
		}
		response.setAccounts(accounts);
		return response;
	}

	@Override
	public String accountToString(Map<String, Object> accountMap) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(accountMap);
	}

	@Override
	public Map<String, Object> stringToAccount(String accountString) throws JsonProcessingException {
		return objectMapper.readValue(accountString, Map.class);
	}

	@Override
	public HashMap<String, String> createNewAccount(AccountDAO account, Authentication authentication) throws JsonProcessingException {
		HashMap<String, String> responseMap = new HashMap<>();
		if(account.getAccountType() < 0 || account.getAccountType() > 2) {
			responseMap.put("message", "You have selected the wrong account type");
			responseMap.put("redirect", "redirect:/user");
			return responseMap;
		}
		int routingNumber = generateRoutingNumber();

		HashMap<String, Object> accountDetailsMap = new HashMap<>();
		accountDetailsMap = getAccountDetailsMap(authentication, account, routingNumber);

		Long userId = bankUserService.getUserByEmail(authentication.getName()).getId();
		String name = bankUserService.getUserByUserId(userId).getName();

		AccountRequestDAO accountRequest = generateAccountRequest(accountDetailsMap, name);

		accountRequestRepository.save(accountRequest);

		responseMap.put("message", "success");
		return responseMap;
	}

	private AccountRequestDAO generateAccountRequest(HashMap<String, Object> accountDetailsMap, String name) throws JsonProcessingException {
		AccountRequestDAO accountRequest = new AccountRequestDAO();
		String accountString = accountService.accountToString(accountDetailsMap);
		accountRequest.setAccount(accountString);
		accountRequest.setCreatedBy(name);
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		Date createdAt = new Date(ts.getTime());
		accountRequest.setCreatedAt(createdAt);
		accountRequest.setType(Constants.NEW_ACCOUNT_REQUEST_TYPE);
		accountRequest.setRole(2);
		accountRequest.setStatusId(Constants.STATUS_PENDING);
		return accountRequest;
	}

	private HashMap<String, Object> getAccountDetailsMap(Authentication authentication, AccountDAO account, int routingNumber) {
		HashMap<String, Object> accountMap = new HashMap<>();
		String emailId = authentication.getName();
		Long userId = bankUserService.getUserByEmail(emailId).getId();
		accountMap.put("accountNo", null);
		accountMap.put("accountType", account.getAccountType());
		accountMap.put("routingNo", routingNumber);
		accountMap.put("userId", userId);
		accountMap.put("balance", 0);
		accountMap.put("interest", 10);
		return accountMap;
	}

	private int generateRoutingNumber() {
		SecureRandom routingRandomizer = new SecureRandom();
		return routingRandomizer.nextInt(100000);
	}

	@Override
	public AccountDAO saveOrUpdate(AccountDAO account) {
		return accountRepository.save(account);
	}

}
