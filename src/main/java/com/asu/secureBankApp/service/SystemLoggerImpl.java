package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Repository.LogRepository;
import com.asu.secureBankApp.dao.SystemLoggerDAO;
import com.asu.secureBankApp.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SystemLoggerImpl implements SystemLoggerService{

    @Autowired
    LogRepository logRepository;

    @Override
    public void log(int user, String dump, String type) {
        SystemLoggerDAO log = new SystemLoggerDAO();
        log.setLogDump(dump);
        log.setLogTime(System.currentTimeMillis());
        log.setLogType(type);
        log.setUserId(user);
        logRepository.save(log);
    }

    @Override
    public List<SystemLoggerDAO> getLogs() {
        return (List<SystemLoggerDAO>) logRepository.findAll();
    }

//    @Override
//    public void getLogsByUser(String user) {
//
//    }
//
//    @Override
//    public void getLogsByType(String logType) {
//
//    }


}
