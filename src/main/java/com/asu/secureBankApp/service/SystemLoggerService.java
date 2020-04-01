package com.asu.secureBankApp.service;

import com.asu.secureBankApp.dao.SystemLoggerDAO;
import com.asu.secureBankApp.dao.UserDAO;

import java.util.List;

public interface SystemLoggerService {

    void log(int user, String dump, String type);

    //void getLogsByUser(String user);

    //void getLogsByType(String logType);

    List<SystemLoggerDAO> getLogs();

}
