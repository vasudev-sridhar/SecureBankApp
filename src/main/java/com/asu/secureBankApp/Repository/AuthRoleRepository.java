package com.asu.secureBankApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.AuthRoleDAO;

@Repository
public interface AuthRoleRepository extends JpaRepository<AuthRoleDAO, Integer> {

}
