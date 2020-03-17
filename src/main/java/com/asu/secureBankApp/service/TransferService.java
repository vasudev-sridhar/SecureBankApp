package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.TransferRequest;

public interface TransferService {
	String transfer(TransferRequest transferReq);
}
