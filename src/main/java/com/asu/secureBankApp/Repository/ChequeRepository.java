package com.asu.secureBankApp.Repository;

import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.ChequeDAO;
import com.asu.secureBankApp.dao.SystemLoggerDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChequeRepository extends CrudRepository<ChequeDAO, Long> {

    List<ChequeDAO> findByStatus(Integer status);

    List<ChequeDAO> findByToAccountAndStatus(AccountDAO toAccount, Integer status);

}