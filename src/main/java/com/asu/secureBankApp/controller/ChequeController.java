package com.asu.secureBankApp.controller;

import com.asu.secureBankApp.Request.ChequeRequest;
import com.asu.secureBankApp.Request.UserRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.ChequeDAO;
import com.asu.secureBankApp.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cheque")
public class ChequeController {

    @Autowired
    ChequeService chequeService;

    @PostMapping(value = "/issue", consumes = { "application/json" })
    public StatusResponse issueCheque(@RequestBody @Valid ChequeRequest cheque) {
        return chequeService.issueCheque(cheque);
    }

    @GetMapping(value = "/listIssueApprovals")
    public @ResponseBody List<ChequeDAO> listChequesForApprovals() {
        return chequeService.listCheques();
    }

}
