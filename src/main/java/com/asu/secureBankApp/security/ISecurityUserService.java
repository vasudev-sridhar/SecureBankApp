package com.asu.secureBankApp.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
