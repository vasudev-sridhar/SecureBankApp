package com.asu.secureBankApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.AuthRolePermissionDAO;

@Repository
public interface AuthRolePermissionRepository extends JpaRepository<AuthRolePermissionDAO, Integer> {

}
