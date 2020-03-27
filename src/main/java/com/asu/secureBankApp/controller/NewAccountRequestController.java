package com.asu.secureBankApp.controller;

import com.asu.secureBankApp.service.NewAccountRequestService;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/accountRequest")
public class NewAccountRequestController {
	
	@Autowired
	NewAccountRequestService accountRequestService;
	
	
	@RequestMapping(value="/list/{page}", method= RequestMethod.GET)
	public HashMap<String, Object> list(@PathVariable("page") int page, Authentication authentication) {
		
		HashMap<String, Object> response = accountRequestService.getList(page, authentication);
		return response;
	}
        
	
}
