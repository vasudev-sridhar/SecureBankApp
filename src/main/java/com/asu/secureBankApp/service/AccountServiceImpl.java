package com.asu.secureBankApp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asu.secureBankApp.Config.Constants;
import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.AccountResponses;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.asu.secureBankApp.dao.Transaction;
import com.asu.secureBankApp.dao.UserDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import constants.ErrorCodes;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	BankUserService userService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	SystemLoggerImpl logService;

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
	
	public Page<AccountDAO> getPaginated(Pageable pageable) {
		return getPatinations(pageable);
	}
	
	public Page<AccountDAO> getPatinations(Pageable pageable) {
		return accountRepository.findAll(pageable);
	}

	@Override
	public HashMap<String, Object> getListPage(Authentication authentication, int page) {
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        HashMap<String, Object> response = new HashMap<>();
        
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        if(roles.contains("TIER1")){
            response.put("view","account_list");
        }else{
            response.put("view","account_list_tier2");
        }

        PageRequest pageable = PageRequest.of(page - 1, 15);
        Page<AccountDAO> accountPage = getPaginated(pageable);
        int totalPages = accountPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            response.put("pageNumbers", pageNumbers);
            
        }
        response.put("accountList", accountPage.getContent());
        AccountDAO account = new AccountDAO();
        response.put("account", account);
		
		return null;
	}

	@Override
	public HashMap<String, Object> depositPost(Transaction transaction, BindingResult bindingResult,
			Authentication authentication, RedirectAttributes redirectAttributes) {
		
		HashMap<String, Object> response = new HashMap<String, Object>();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        String name;
        int role;
        ModelAndView modelAndView;
        
        UserDAO user = userService.getUserByEmail(authentication.getName());
        int id = user.getId();
        name = user.getName();
        
        if(roles.contains("TIER1")){
        	role = Constants.TIER1;
        	response.put("redirect", "redirect:/account/deposit");
        }else{
        	role = Constants.USER;
        	response.put("redirect", "redirect:/account/deposit1");
        }
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("message","Please fix the errors");
//            return modelAndView;
//        }
        String message = depositWithdraw(transaction, name, role, authentication, Constants.CREDIT);
        if(message.contains("Success")){
            logService.log(user.getId(), "Deposited money for account "+transaction.getAccount_no()+" $"+transaction.getTransaction_amt(), "Deposit");
        }
        response.put("message", message);
		return response;
	}
	
	String depositWithdraw(Transaction transaction, String name, int role, Authentication authentication, int type) {
		
		boolean isUser = checkUser(role);
		UserDAO user;
		if(isUser) {
			boolean validUserAccount = false;
			user = userService.getUserByEmail(authentication.getName());
			List<AccountDAO> userAccounts = user.getAccounts();
			for(int i=0; i<userAccounts.size(); i++) {
				if(transaction.getAccount_no().equals(userAccounts.get(i).getId())) {
					validUserAccount = true;
				}
			}
			if(!validUserAccount)
				return "User is not Authorized or Invalid Account Number";
		}
		AccountDAO account;
		try{
			account = accountService.getAccountByAccountNo(transaction.getAccount_no());
        }
        catch(Exception e){
        	String response = "Please enter a valid Account Number!";
        	return response;
        }
		if(transaction.getTransaction_amt()<0) {
			String response = "Transaction Amount cannot be negative";
			return response;
		}
		Double bal = account.getBalance();
		if(type == Constants.DEBIT) {
			if(checkInsufficient(bal, transaction.getTransaction_amt())) {
				return "Insufficient Balance";
			}
			else {
				Double remainingBalance = bal - transaction.getTransaction_amt();
				account.setBalance(remainingBalance);
				transaction = setTransaction(transaction, remainingBalance, Constants.DEBIT);
			}
		}
		else {
			Double addBalance = bal + transaction.getTransaction_amt();
			account.setBalance(addBalance);
			transaction = setTransaction(transaction, addBalance, Constants.DEBIT);
		}
		
		if(transaction.getTransaction_amt()<Constants.LIMIT && role == Constants.USER) {
            transaction.setStatus(Constants.APPROVED);
            accountService.saveOrUpdate(account);
		}
		else {
			
		}
		
		
		return null;
	}
	
	private Transaction setTransaction(Transaction transaction, Double addBalance, int debit) {
		transaction.setBalance(addBalance);
		transaction.setTransaction_type(debit);
		return transaction;
	}

	private boolean checkInsufficient(Double bal, Double transaction_amt) {
		if(bal - transaction_amt < 0)
			return false;
		else
			return true;
	}

	boolean checkUser(int role) {
		if(role == Constants.USER) {
			return true;
		}
		else
			return false;
	}

	@Override
	public AccountDAO getAccountByAccountNo(Integer accNo) {
		return accountRepository.findById(accNo).get();
	}

	@Override
	public AccountDAO saveOrUpdate(AccountDAO account) {
		return accountRepository.save(account);
	}

	@Override
	public HashMap<String, Object> depositTier1(String message) {
		HashMap<String, Object> response = new HashMap<String, Object>();
    	response.put("modelAndView", "deposit");
        Transaction transaction = new Transaction();
        response.put("transaction", transaction);
        response.put("message", message);
		return response;
	}

	@Override
	public HashMap<String, Object> depositUser(String message, Authentication authentication) {
		HashMap<String, Object> response = new HashMap<String, Object>();
    	response.put("modelAndView", "deposit_user");
        Transaction transaction = new Transaction();
        response.put("transaction", transaction);
        response.put("message", message);
        List<AccountDAO> accounts = userService.getAccountsByEmail(authentication.getName());
        response.put("accounts", accounts);
		
		return response;
	}
	

}
