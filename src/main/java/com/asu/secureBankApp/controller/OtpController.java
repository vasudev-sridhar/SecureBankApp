package com.asu.secureBankApp.controller;

import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.asu.secureBankApp.Repository.AuthUserRepository;
import com.asu.secureBankApp.dao.AuthUserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Response.OtpResponse;
import com.asu.secureBankApp.service.MailService;
import com.asu.secureBankApp.service.OtpServiceImpl;
import com.asu.secureBankApp.service.UserService;

@RestController
@RequestMapping("/api")
public class OtpController {
	
	@Autowired
	OtpServiceImpl otpService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	AuthUserRepository authUserRepository;
	
	int windowMillis =5000;
	
	@RequestMapping(value="/generateOtp", method= RequestMethod.GET)
    public OtpResponse generateOtp(Authentication authentication) {
		String username = authentication.getName();
		OtpResponse response = new OtpResponse();
		response.setIsSuccess(false);
        try {
            String r = otpService.generateBase32Secret();
            String otp = otpService.generateCurrentNumberString(r);
        	//String mail= userService.findById(username).getEmailId();
			String mail= userRepository.findByUsername(username).getEmailId();
        	AuthUserDAO authUser = userService.findByEmail(mail).orElseGet(null);
        	if(authUser != null) {
	            authUser.setOtp(otp);
	            authUser.setr(r);
	            authUser.setExpiry(new Timestamp(System.currentTimeMillis()));
//	            userService.saveOrUpdate(authUser);
				authUserRepository.save(authUser);
        	}else {
        		return response;
        	}
            
            MailService.sendmail(mail,otp);
        }catch (Exception e){
            return response;
        }
        response.setIsSuccess(true);
        return  response;
    }
	
	@RequestMapping(value="/verifyOtp")
	///{otp:.+}/{username:.+}", method= RequestMethod.GET)
	public OtpResponse verifyOtp(@RequestParam("otp") int otp, Authentication authentication)
			throws GeneralSecurityException {
		String username = authentication.getName();
//		String mail1= userService.findById(username).getEmailId();
		String mail1= userRepository.findByUsername(username).getEmailId();
		AuthUserDAO authUser1 = userService.findByEmail(mail1).orElseGet(null);
		String r = authUser1.getr();
		int otp1 = authUser1.getOtp();
		//String otp2 = String.valueOf(otp);
		OtpResponse response1 = new OtpResponse();
		if (otp1 == otp) {
			response1.setIsSuccess(true);
			return response1;
		}
		else {
			response1.setIsSuccess(true);
			return response1;
		}
//		return validateCurrentNumber(r, otp, windowMillis, System.currentTimeMillis(),
//				10, response);
	}
}
