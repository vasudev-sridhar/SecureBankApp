package com.asu.secureBankApp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.UserDAO;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Integer> {

	UserDAO findByUsername(String username);
	
	UserDAO findByUsernameAndPassword(String username, String password);

	UserDAO findByEmailId(String emailId);

	UserDAO findByContact(String contact);

	UserDAO findById(Long id);

}