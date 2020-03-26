package com.asu.secureBankApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.TransactionDAO;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDAO, Integer>{

}
