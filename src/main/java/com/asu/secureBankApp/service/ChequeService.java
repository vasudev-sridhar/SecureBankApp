package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.ChequeRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import org.springframework.stereotype.Service;

@Service
public interface ChequeService {


    StatusResponse issueCheque(ChequeRequest cheque);
}
