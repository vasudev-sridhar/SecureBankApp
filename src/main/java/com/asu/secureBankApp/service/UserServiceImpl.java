package com.asu.secureBankApp.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Repository.AuthRoleRepository;
import com.asu.secureBankApp.Repository.AuthUserRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.UserDOBRequest;
import com.asu.secureBankApp.Request.UserRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AuthRoleDAO;
import com.asu.secureBankApp.dao.AuthUserDAO;
import com.asu.secureBankApp.dao.UserDAO;

import constants.ErrorCodes;
import constants.RoleType;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthRoleRepository authRoleRepository;
	
	@Autowired
	private AuthUserRepository authUserRepository;

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
    @Override
    public List<UserDAO> getAllUsers(Authentication auth) throws Exception {
    	UserDAO authUser = userRepository.findByUsername(auth.getPrincipal().toString());
    	RoleType authRoleType = authUser.getAuthRole()
				.getRoleType();
    	if(authRoleType == RoleType.USER || authRoleType == RoleType.MERCHANT)
    		throw new Exception(ErrorCodes.INVALID_ACCESS);
    	
    	List<RoleType> roles = new ArrayList<>();
    	roles.add(RoleType.USER);
    	roles.add(RoleType.MERCHANT);
    	if(authRoleType == RoleType.ADMIN) {
    		roles.add(RoleType.TIER1);
    		roles.add(RoleType.TIER2);
    	}
    	
    	List<UserDAO> users =  userRepository.findByAuthRole_RoleTypeIn(roles);
    	for(UserDAO user : users) {
    		user.getAuthRole().setPermissions(null);
    		user.setAccounts(null);
    	}
    	return users;
    }
//
    @Override
    public UserDAO getUser(Integer userId, Authentication auth) {
    	UserDAO userDAO = userRepository.findById(userId).get();
    	userDAO.getAuthRole().setPermissions(null);
    	userDAO.setAccounts(null);
    	return userDAO;
    }
    
    public StatusResponse updateEmail(UserRequest userReq) {
    	StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);	
		UserDAO userDAO = userRepository.findById(userReq.getUserid()).orElseGet(null);
		userDAO.setEmailId(userReq.getNewInfo());
		userRepository.save(userDAO);
		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);	
		return response;
    }
    
    public StatusResponse updateAddress(UserRequest userReq) {
    	StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);	
		UserDAO userDAO = userRepository.findById(userReq.getUserid()).orElseGet(null);
		userDAO.setAddress(userReq.getNewInfo());
		userRepository.save(userDAO);
		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);	
		return response;
    }
    
    public StatusResponse updatePhone(UserRequest userReq) {
    	StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);	
		UserDAO userDAO = userRepository.findById(userReq.getUserid()).orElseGet(null);
		userDAO.setContact(userReq.getNewInfo());
		userRepository.save(userDAO);
		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);	
		return response;
    }
    public StatusResponse updateDOB(UserDOBRequest userReq) {
    	StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);	
		UserDAO userDAO = userRepository.findById(userReq.getUserid()).orElseGet(null);
		userDAO.setDob(userReq.getNewInfo());
		userRepository.save(userDAO);
		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);	
		return response;
    }

	public String checkIfUsernameExist(String username) {
		//StatusResponse response = new StatusResponse();
		UserDAO user = userRepository.findByUsername(username);
		if(null != user)
			return "0";
		else
			return "1";
	}

	@Transactional
	public StatusResponse signup(UserDAO newUser) {
		AuthRoleDAO authRole = new AuthRoleDAO();
		authRole.setId(4);
		authRole = authRoleRepository.findById(4).orElseGet(null);
		newUser.setId(null);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] hashInBytes = md.digest(newUser.getPassword().getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
		newUser.setPassword(sb.toString());
		newUser.setAuthRole(authRole);
		newUser.setAccounts(null);
		newUser.setCreated(Calendar.getInstance().getTime());
		System.out.println(newUser.toString());
		userRepository.save(newUser);
		StatusResponse response = new StatusResponse();
		response.setIsSuccess(true);
		return response;
	}
	
	@Override
    public Optional<AuthUserDAO> findByEmail(String email){
        return authUserRepository.findById(userRepository.findByEmailId(email).getId());
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