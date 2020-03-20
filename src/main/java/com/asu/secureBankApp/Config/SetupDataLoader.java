package com.asu.secureBankApp.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.asu.secureBankApp.Repository.AuthRoleRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.dao.AuthRoleDAO;
import com.asu.secureBankApp.dao.AuthRolePermissionDAO;

@Component
public class SetupDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private AuthRoleRepository roleRepository;
  
	/*
	 * @Autowired private PrivilegeRepository privilegeRepository;
	 * 
	 * @Autowired private PasswordEncoder passwordEncoder;
	 */
  
    private Map<AuthRoleDAO, List<AuthRolePermissionDAO>> rolePermissionsMap;
    
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;
        List<AuthRoleDAO> roles = roleRepository.findAll();
        if(roles == null || roles.size() == 0) {
        	System.out.println("Empty roles. Exiting...");
        	return;
        }
        rolePermissionsMap = new HashMap<>();
        for(AuthRoleDAO role : roles) {
        	rolePermissionsMap.put(role, role.getPermissions());
        	System.out.println("Role: " + role.getRoleType() + " permission: " + role.getPermissions().get(0).getAuthPermission().getId());
        }
        System.out.println("rolePermissionsMap done");
 
        alreadySetup = true;
    }

}