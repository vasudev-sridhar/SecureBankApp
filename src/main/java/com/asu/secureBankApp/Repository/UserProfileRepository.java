package com.asu.secureBankApp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.UserProfileDAO;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfileDAO, Integer> {

	UserProfileDAO findByUsername(String username);
	
	UserProfileDAO findByUsernameAndPassword(String username, String password);
}