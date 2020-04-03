package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.ChequeRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.ChequeDAO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChequeService {

    StatusResponse issueCheque(ChequeRequest cheque);

    List<ChequeDAO> listCheques();
}
