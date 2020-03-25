package com.asu.secureBankApp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asu.secureBankApp.dao.AuthRoleDAO;
import com.asu.secureBankApp.dao.AuthUserDAO;
import com.asu.secureBankApp.dao.UserDAO;

public interface UserService {

	Page<UserDAO> getPaginated(Pageable pageable);

    Long findUserByEmail(String email);

    Long findUserByPhone(String phone);

    AuthUserDAO saveUserDAO (AuthUserDAO UserDAO);

    boolean UserDAOAlreadyExist (AuthUserDAO UserDAO);

    List<AuthUserDAO> getAllUserDAOs();

    UserDAO getUserByUserId(Long UserDAOId);

    void saveOrUpdate(AuthUserDAO UserDAO);

    void deleteUserDAO(Integer UserDAOId);

    UserDAO saveOrUpdate(UserDAO UserDAO);

    void save(AuthRoleDAO authRoleDAO);

    void deleteAuthUserDAORole(AuthRoleDAO authRoleDAO);

    void deleteAuthUserDAO(Integer authUserDAOId);

    AuthUserDAO findByEmail(String email);

    AuthRoleDAO findById(AuthRoleDAO authUserDAORolePK);
}
