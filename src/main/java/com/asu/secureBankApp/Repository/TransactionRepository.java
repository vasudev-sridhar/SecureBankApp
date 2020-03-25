package com.asu.secureBankApp.Repository;

import com.asu.secureBankApp.dao.SystemLoggerDAO;
import com.asu.secureBankApp.dao.TransactionDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionDAO, Integer> {

    List<TransactionDAO> findAllByAccountNo(int accountNo);

}
