package com.asu.secureBankApp.Repository;

import java.util.List;
import java.util.Optional;

import com.asu.secureBankApp.dao.AuthUserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.AuthUserDAO;
import com.asu.secureBankApp.dao.UserDAO;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserDAO, Integer> {

//	Optional<AuthUserDAO> findById(Integer id);
//	List<AuthUserDAO> findByUser(UserDAO user);
	
    @Query("SELECT u FROM auth_user u WHERE u.email =:email")
    AuthUserDAO findUserByEmail(@Param("email") String email);
}