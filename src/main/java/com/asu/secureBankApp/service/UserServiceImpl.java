package com.asu.secureBankApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
    private UserRepository userRepository;

//    @Autowired
//    private  AuthUserRepository authUserRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder encoder;
//
//    @Autowired
//    private AuthUserRoleRepository authUserRoleRepository;
//
//    @Override
//    public Page<UserDAO> getPaginated(Pageable pageable) {
//        return userRepository.findAll(pageable);
//    }
//
//    @Override
//    public Long findUserByEmail(String email){
//        return userRepository.findUserByEmail(email);
//    }
//
//    @Override
//    public Long findUserByPhone(String phone){
//        return userRepository.findUserByPhone(phone);
//    }
//
//    @Override
//    public List<Auth_user> getAllUsers() {
//        return (List<Auth_user>) authUserRepository.findAll();
//    }
//
    @Override
    public UserDAO getUser(Integer userId, Authentication auth) {
    	UserDAO userDAO = userRepository.findById(userId).get();
    	userDAO.getAuthRole().setPermissions(null);
    	userDAO.setAccounts(null);
    	return userDAO;
    }
//
//    @Override
//    public void saveOrUpdate(Auth_user user) {
//        authUserRepository.save(user);
//    }
//
//    @Override
//    public UserDAO saveOrUpdate(UserDAO user) {
//        return userRepository.save(user);
//
//    }
//
//    @Override
//    public void deleteUser(Integer userId) {
//        authUserRepository.deleteById(userId);
//    }
//
//    @Override
//    public Auth_user saveUser (Auth_user user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setStatus ("VERIFIED");
//        Auth_role userRole = roleRepository.findByRole("xxxxxx");    // SITE_USER or what not
//        user.setRoles (new HashSet<Auth_role>(Arrays.asList(userRole)));
//        return authUserRepository.save(user);
//    }
//
//
//    @Override
//    public boolean userAlreadyExist (Auth_user user) {
//        if(authUserRepository.findUserByEmail(user.getEmail()) == null){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    @Override
//    public void save(AuthUserRole authUserRole){
//        authUserRoleRepository.save(authUserRole);
//    }
//
//    @Override
//    public Auth_user findByEmail(String email){
//        return authUserRepository.findUserByEmail(email);
//    }
//
//    @Override
//    public void deleteAuthUserRole(AuthUserRole authUserRole){
//        authUserRoleRepository.delete(authUserRole);
//    }
//
//    @Override
//    public void deleteAuthUser(Integer authUserId){
//        authUserRepository.deleteById(authUserId);
//    }
//
//    @Override
//    public AuthUserRole findById(AuthUserRolePK authUserRolePK){
//        return authUserRoleRepository.findById(authUserRolePK).get();
//    }
}
