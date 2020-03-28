package com.asu.secureBankApp.service;

import com.asu.secureBankApp.service.BankUserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Config.Constants;
import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.AccountRequestRepository;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.AccountRequestDAO;
import com.asu.secureBankApp.dao.UserDAO;

import constants.RoleType;

@Service
@Transactional
public class NewAccountRequestServiceImpl implements NewAccountRequestService {
	
	@Autowired
	AccountRequestRepository accountRequestRepository;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	BankUserService bankUserService;
	
	@Autowired
	SystemLoggerService systemLoggerService;

	@Override
	public HashMap<String, Object> getList(int page, Authentication authentication) {
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        Integer role;     
        if(roles.contains("ADMIN")){
            role = 1;
            response.put("modelAndView", "account_request_admin");
        }else if(roles.contains("TIER1")){
            role = 2;
            response.put("modelAndView", "account_request");
        }
        else{
            role = 3;
            response.put("modelAndView", "account_request");
        }

        PageRequest pageable = PageRequest.of(page - 1, 10);
        Page<AccountRequestDAO> requestPage = getPaginated(pageable, role);
        int totalPages = requestPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNums = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            response.put("pageNums", pageNums);
        }
        response.put("role", role);
        response.put("activeRequestList", true);
        response.put("requestList", requestPage.getContent());
        return response;
    }
	
	Page<AccountRequestDAO> getPaginated(Pageable pageable, Integer role){
		return accountRequestRepository.findAll(pageable, role);
	}

	@Override
	public HashMap<String, Object> getApproval(AccountRequestDAO accountRequest, Authentication authentication) {
		
		UserDAO user = bankUserService.getUserByEmail(authentication.getName());
		HashMap<String, Object> response = new HashMap<>();
//		Long userId =  employeeService.findUserByEmail(authentication.getName());
//        String name = employeeService.getEmployeeById(userId).getEmployee_name();
        if(accountRequest.getType() == Constants.NEW_ACCOUNT_REQUEST_TYPE){
        	String accountString = accountRequest.getAccount();
        	Map<String, Object> attributes;
			try {
				attributes = accountService.stringToAccount(accountString);
	            AccountDAO account = new AccountDAO();
	            account.setId((int)attributes.get("account_no"));
	            account.setUser(user);
	            double balance = (double)attributes.get("balance");
	            account.setBalance((double)balance);
	            account.setRoutingNo((int)attributes.get("routing_no"));
	            account.setAccountType((Integer)attributes.get("account_type"));
	            double interest = (double)attributes.get("interest");
	            account.setInterest((double)interest);
	            Timestamp ts=new Timestamp(System.currentTimeMillis());  
	            Date date = new Date(ts.getTime());
	            account.setCreated(date);
	            account.setUpdated(date);
	            AccountDAO new_account = accountService.saveOrUpdate(account);
			} catch (JsonProcessingException e) {
				response.put("modelAndView", "Failed");
			}
        }
        accountRequest.setApproved_at(new Timestamp(System.currentTimeMillis()));
        accountRequest.setApproved_by(user.getName());
        accountRequest.setStatus_id(Constants.STATUS_APPROVED);
        saveOrUpdate(accountRequest);
        systemLoggerService.log(user.getId(), "Approved Request for id:"+accountRequest.getRequest_id(), "Account Approved");
        response.put("modelAndView", "redirect:/account-request/list/1");
        return response;
	}

	@Override
	public AccountRequestDAO getAccountRequestByReqId(Long reqId) {
		return accountRequestRepository.findById(reqId).get();
	}
	
	void saveOrUpdate(AccountRequestDAO accountRequest) {
		accountRequestRepository.save(accountRequest);
	}

	@Override
	public HashMap<String, Object> decline(AccountRequestDAO accountRequest, Authentication authentication) {
		
		UserDAO user = bankUserService.getUserByEmail(authentication.getName());
		HashMap<String, Object> response = new HashMap<>();
		accountRequest.setApproved_at((new Timestamp(System.currentTimeMillis())));
		accountRequest.setApproved_by(user.getName());
		accountRequest.setStatus_id(Constants.STATUS_DECLINED);
		saveOrUpdate(accountRequest);
        systemLoggerService.log(user.getId(), "Declined Request for id:"+accountRequest.getRequest_id(), "Declined");
        response.put("modelAndView", "redirect:/account-request/list/1");
        return response;
	}
		
	

}
