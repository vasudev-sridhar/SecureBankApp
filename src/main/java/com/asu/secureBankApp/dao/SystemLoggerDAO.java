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
    private Long logId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "log_time")
    private long logTime;

    @Column(name = "log_type")
    private String logType;

    @Column(name = "log_dump")
    private String logDump;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
