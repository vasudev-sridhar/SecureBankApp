package com.asu.secureBankApp.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.TransactionDAO;
import com.asu.secureBankApp.dao.UserDAO;

import constants.TransactionStatus;
import constants.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDAO, Integer>{
	
	TransactionDAO findByTransactionId(Integer id);

	List<TransactionDAO> findByApprovedByAndTypeAndStatusIn(UserDAO approvedBy, TransactionType type, List<TransactionStatus> status);
	
	List<TransactionDAO> findByTypeAndStatusIn(TransactionType type, List<TransactionStatus> status);
	
	List<TransactionDAO> findByFromAccount_UserAndTypeAndStatusIn(UserDAO fromUser, TransactionType type, List<TransactionStatus> status);
	
	List<TransactionDAO> findByApprovedByAndStatusIn(UserDAO approvedBy, List<TransactionStatus> status);
	
	List<TransactionDAO> findByStatusIn(List<TransactionStatus> status);
	
	List<TransactionDAO> findByFromAccount_UserAndStatusIn(UserDAO fromUser, List<TransactionStatus> status);
	
	List<TransactionDAO> findByStatusInAndIsCritical(List<TransactionStatus> status, boolean isCritical);
	
	List<TransactionDAO> findByFromAccount_User(UserDAO fromUser);
	
	List<TransactionDAO> findByFromAccount_UserAndTransactionTimestampGreaterThan(UserDAO fromUser, Date date);
	
	@Query("SELECT SUM(t.transactionAmount) from transaction t where t.transactionTimestamp > CURDATE() and t.fromAccount.user = :fromUser")
	Float dailyTransactionSum(UserDAO fromUser);
}
