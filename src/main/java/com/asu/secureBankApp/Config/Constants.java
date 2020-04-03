package com.asu.secureBankApp.Config;

public class Constants {

    public static final String LOGIN_TIME = "login_time";
    public static final String LOGOUT_TIME = "logout_time";
    public static final float UPDATE_BALANCE_CRITICAL_LIMIT = 1000.0f;
    public static final float TRANSFER_CRITICAL_LIMIT = 1000.0f;

    public static final int SAVINGS_ACCOUNT_TYPE = 0;
    public static final int CHECKINGS_ACCOUNT_TYPE = 1;
    public static final int CREDIT_CARD_ACCOUNT_TYPE = 2;

    public static final int NEW_ACCOUNT_REQUEST_TYPE = 0;
    public static final int ADDITIONAL_ACCOUNT_REQUEST_TYPE = 1;
    
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_APPROVED = 1;
    public static final int STATUS_DECLINED = 2;

    public static final int CHEQUE_ISSUE_PENDING = 0;
    public static final int CHEQUE_ISSUE_APPROVED = 1;
    public static final int CHEQUE_DEPOSIT_PENDING = 2;
    public static final int CHEQUE_DEPOSIT_APPROVED = 3;

}
