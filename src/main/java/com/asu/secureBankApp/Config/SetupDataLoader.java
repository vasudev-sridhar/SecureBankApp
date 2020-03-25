package com.asu.secureBankApp.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.asu.secureBankApp.Repository.AuthRoleRepository;
import com.asu.secureBankApp.dao.AuthRoleDAO;
import com.asu.secureBankApp.dao.AuthRolePermissionDAO;

import constants.RoleType;

@Component
@Configuration
public class SetupDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
  
    @Autowired
    private AuthRoleRepository roleRepository;
    
    public static RPMap rpMap;
    
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
        Map<RoleType, List<AuthRolePermissionDAO>> rolePermissionsMap = new HashMap<>();
        for(AuthRoleDAO role : roles) {
        	rolePermissionsMap.put(role.getRoleType(), role.getPermissions());
        	System.out.println("Role: " + role.getRoleType() + " permission: " + role.getPermissions().get(0).getAuthPermission().getId());
        }
        rolePermissionsMap = new HashMap<>();
        System.out.println("rolePermissionsMap done");
        rpMap = new RPMap(rolePermissionsMap);
 
        alreadySetup = true;
    }
    
    @Component
    public static class RPMap {
    	public Map<RoleType, List<AuthRolePermissionDAO>> rpMap;
    	public RPMap(Map<RoleType, List<AuthRolePermissionDAO>> r) {
    		rpMap = r;  		
    	}
    }
}
