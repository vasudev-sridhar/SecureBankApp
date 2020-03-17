package com.asu.secureBankApp.Repository;

import com.asu.secureBankApp.dao.SystemLoggerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<SystemLoggerDAO, Integer> {

}
