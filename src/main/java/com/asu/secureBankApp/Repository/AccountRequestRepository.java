package com.asu.secureBankApp.Repository;

import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.AccountRequestDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface AccountRequestRepository extends CrudRepository<AccountRequestDAO, Long>{

    @Query("SELECT t FROM account_request t WHERE t.role = :role")
    Page<AccountRequestDAO> findAll(Pageable pageable, @Param("role") int role);
	
}
