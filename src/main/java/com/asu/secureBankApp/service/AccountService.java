package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;

public interface AccountService {

	StatusResponse updateBalance(UpdateBalanceRequest updateBalanceRequest);
}
