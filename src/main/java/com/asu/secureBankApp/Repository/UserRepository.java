package com.asu.secureBankApp.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.UserDAO;

import constants.RoleType;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Integer> {

	UserDAO findByUsername(String username);
	
	UserDAO findByUsernameAndPassword(String username, String password);

	UserDAO findByEmailId(String emailId);

	UserDAO findByContact(String contact);
	
	List<UserDAO> findByAuthRole_RoleTypeIn(List<RoleType> roles);

}