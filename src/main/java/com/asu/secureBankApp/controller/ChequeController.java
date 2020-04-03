package com.asu.secureBankApp.controller;

import com.asu.secureBankApp.Request.ChequeRequest;
import com.asu.secureBankApp.Request.UserRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/cheque")
public class ChequeController {

    @Autowired
    ChequeService chequeService;

    @PostMapping(value = "/issue")
    public StatusResponse issueCheque(@RequestBody @Valid ChequeRequest cheque) {
        return chequeService.issueCheque(cheque);
    }

}
