package com.asu.secureBankApp.controller;

import com.asu.secureBankApp.dao.SystemLoggerDAO;
import com.asu.secureBankApp.service.SystemLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/log")
public class SystemLoggerController {

    @Autowired
    SystemLoggerService systemLoggerService;

    @GetMapping(value = "/list")
    List<SystemLoggerDAO> getSystemLogs() {
        return systemLoggerService.getLogs();
    }

}
