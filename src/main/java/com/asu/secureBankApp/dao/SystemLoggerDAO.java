package com.asu.secureBankApp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "system_log")
public class SystemLoggerDAO {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer logId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "log_time")
    private long logTime;

    @Column(name = "log_type")
    private String logType;

    @Column(name = "log_dump")
    private String logDump;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public long getLogTime() {
        return logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogDump() {
        return logDump;
    }

    public void setLogDump(String logDump) {
        this.logDump = logDump;
    }

}
