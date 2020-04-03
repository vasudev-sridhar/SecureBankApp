package com.asu.secureBankApp.controller;

import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Repository.AuthUserRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Response.OtpResponse;
import com.asu.secureBankApp.dao.AuthUserDAO;
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
        	List<AuthUserDAO> authUsers = userService.findByEmail(mail);
        	if(authUsers != null && authUsers.size() > 0) {
        		AuthUserDAO authUser = authUsers.get(authUsers.size()-1);
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
        	e.printStackTrace();
            return response;
        }
        response.setIsSuccess(true);
        return  response;
    }
	
	@RequestMapping(value="/verifyOtp")
	///{otp:.+}/{username:.+}", method= RequestMethod.GET)
	public OtpResponse verifyOtp(@RequestParam("otp") int otp, Authentication authentication)
			throws GeneralSecurityException {
		OtpResponse response1 = new OtpResponse();
		response1.setIsSuccess(false);
		String username = authentication.getName();
//		String mail1= userService.findById(username).getEmailId();
		String mail1= userRepository.findByUsername(username).getEmailId();
		List<AuthUserDAO> authUsers = userService.findByEmail(mail1);
		AuthUserDAO authUser = authUsers.get(authUsers.size()-1);
    	if(authUsers != null && authUsers.size() > 0) {
    		String r = authUser.getr();
    		int otp1 = authUser.getOtp();
    		//String otp2 = String.valueOf(otp);
    		if (otp1 == otp) {
    			response1.setIsSuccess(true);
    			return response1;
    		}
    		else {
    			response1.setIsSuccess(true);
    			return response1;
    		}
    		
    	}
		return response1;
    		
//		return validateCurrentNumber(r, otp, windowMillis, System.currentTimeMillis(),
//				10, response);
	}
}
