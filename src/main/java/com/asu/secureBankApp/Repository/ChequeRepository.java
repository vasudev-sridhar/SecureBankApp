package com.asu.secureBankApp.Repository;

import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.ChequeDAO;
import com.asu.secureBankApp.dao.SystemLoggerDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequeRepository extends CrudRepository<ChequeDAO, Integer> {

    AccountDAO findByFromAccount(AccountDAO fromAccount);
    AccountDAO findByToAccount(AccountDAO toAccount);

}