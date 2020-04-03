package com.asu.secureBankApp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.AuthUserDAO;
import com.asu.secureBankApp.dao.UserDAO;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserDAO, Integer> {

	Optional<AuthUserDAO> findById(Integer id);
	
	List<AuthUserDAO> findByUser(UserDAO user);

	List<AuthUserDAO> findByUserId(Integer id);
}