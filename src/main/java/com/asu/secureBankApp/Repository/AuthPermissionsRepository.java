package com.asu.secureBankApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.AuthPermissionsDAO;

@Repository
public interface AuthPermissionsRepository extends JpaRepository<AuthPermissionsDAO, Integer> {

}
