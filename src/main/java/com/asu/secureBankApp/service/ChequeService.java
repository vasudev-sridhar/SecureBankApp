package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.ChequeRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.ChequeDAO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChequeService {

    StatusResponse issueCheque(ChequeRequest cheque);

    List<ChequeDAO> listCheques();

    StatusResponse approveChequeIssue(Long chequeId, Authentication authentication);

    StatusResponse rejectChequeIssue(Long chequeId);
}
