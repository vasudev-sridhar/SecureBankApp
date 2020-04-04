package com.asu.secureBankApp.service;

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
import com.asu.secureBankApp.Repository.UserRepository;
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
	
	@Autowired
	UserRepository userRepository;

	@Override
	public HashMap<String, Object> getList(Authentication authentication) {
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        
		UserDAO authUser = userRepository.findByUsername(authentication.getPrincipal().toString());
		RoleType authRoleType = authUser.getAuthRole()
				.getRoleType();
		
        for(GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        Integer role;     
        if(authRoleType == RoleType.ADMIN){
            role = 1;
            response.put("modelAndView", "account_request_admin");
        }else if(authRoleType == RoleType.TIER1){
            role = 2;
            response.put("modelAndView", "account_request");
        }
        else{
            role = 3;
            response.put("modelAndView", "account_request");
        }

//        //PageRequest pageable = PageRequest.of(page - 1, 10);
//        Page<AccountRequestDAO> requestPage = getPaginated(pageable, role);
//        int totalPages = requestPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNums = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//            response.put("pageNums", pageNums);
//        }
		List<AccountRequestDAO> requestList = getApprovalsByRole(role);
        response.put("role", role);
        response.put("activeRequestList", true);
        response.put("requestList", requestList);
        return response;
    }
	
	List<AccountRequestDAO> getApprovalsByRole(Integer role){
		return accountRequestRepository.findAllByRole(role);
	}

	@Override
	public HashMap<String, Object> getApproval(AccountRequestDAO accountRequest, Authentication authentication) {
		
		
		UserDAO user = userRepository.findByUsername(authentication.getName());
		HashMap<String, Object> response = new HashMap<>();
//		Long userId =  employeeService.findUserByEmail(authentication.getName());
//        String name = employeeService.getEmployeeById(userId).getEmployee_name();
        if(accountRequest.getType() == Constants.NEW_ACCOUNT_REQUEST_TYPE){
        	String accountString = accountRequest.getAccount();
        	Map<String, Object> attributes;
			try {
				attributes = accountService.stringToAccount(accountString);
	            AccountDAO account = new AccountDAO();
	            account.setUser(user);
//	            int balance = (int) attributes.get("balance");
	            account.setBalance(new Double(attributes.get("balance").toString()));
	            account.setAccountType((Integer)attributes.get("accountType"));
//	            int attrInterest = (int) attributes.get("interest");
	            double interest = (new Double(attributes.get("interest").toString()));
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
        Timestamp ts=new Timestamp(System.currentTimeMillis());
        Date date = new Date(ts.getTime());
        accountRequest.setApprovedAt(date);
        accountRequest.setApprovedBy(user.getName());
        accountRequest.setStatusId(Constants.STATUS_APPROVED);
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

		UserDAO user = userRepository.findByUsername(authentication.getName());
		HashMap<String, Object> response = new HashMap<>();
        Timestamp ts=new Timestamp(System.currentTimeMillis());
        Date approvedAt = new Date(ts.getTime());
		accountRequest.setApprovedAt(approvedAt);
		accountRequest.setApprovedBy(user.getName());
		accountRequest.setStatusId(Constants.STATUS_DECLINED);
		saveOrUpdate(accountRequest);
        systemLoggerService.log(user.getId(), "Declined Request for id:"+accountRequest.getRequest_id(), "Declined");
        response.put("modelAndView", "redirect:/account-request/list/1");
        return response;
	}
		
	

}
