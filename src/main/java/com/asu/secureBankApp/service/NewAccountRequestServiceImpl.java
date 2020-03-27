package com.asu.secureBankApp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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


import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.AccountRequestRepository;
import com.asu.secureBankApp.dao.AccountRequestDAO;

import constants.RoleType;

@Service
@Transactional
public class NewAccountRequestServiceImpl implements NewAccountRequestService {
	
	@Autowired
	AccountRequestRepository accountRequestRepository;

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
		
	

}
