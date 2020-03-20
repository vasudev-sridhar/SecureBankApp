package com.asu.secureBankApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.dao.UserDAO;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        //String verificationCode
        //        = ((CustomWebAuthenticationDetails) auth.getDetails())
        //        .getVerificationCode();
    	// Integer id = Integer.parseInt(s)
    	System.out.println("In Authenticationing...");
        UserDAO auth_user = userRepository.findByUsername(auth.getName());
        if(auth_user == null){
            throw new BadCredentialsException("Wrong credentials");
        }
//        Timestamp time = auth_user.getExpiry();
//        time.setTime(time.getTime() + TimeUnit.MINUTES.toMillis(10));
//        if (time.before(new Timestamp(System.currentTimeMillis()))) {
//            throw new BadCredentialsException("OTP expired.");
//        }
//        try {
//            if (auth_user.getOtp() != Integer.parseInt(verificationCode)) {
//                throw new BadCredentialsException("Invalid OTP");
//            }
//        }
//        catch (Exception e){
//            throw new BadCredentialsException("Invalid OTP");
//        }
        Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(
                auth_user.getEmailId(), result.getCredentials(), result.getAuthorities());
    }

    //@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}