package com.asu.secureBankApp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asu.secureBankApp.Config.SetupDataLoader;
import com.asu.secureBankApp.Config.SetupDataLoader.RPMap;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.dao.AuthRolePermissionDAO;
import com.asu.secureBankApp.dao.UserDAO;

import constants.RoleType;

@Service("userDetailsService")
@Transactional
@Configuration
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;
//
//    @Autowired
//    private RPMap rpMap;
    
    @Autowired
    SetupDataLoader setupData;
    
    public MyUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    	System.out.println("In UserDetailService loadUserByUsername");
        final String ip = getClientIP();
        boolean isAccountNonLocked = true;
        if (loginAttemptService.isBlocked(ip)) {
        	System.out.println("User blocked");
        	isAccountNonLocked = false;
            //throw new RuntimeException("blocked");
        }

        try {
        	final UserDAO user = userRepository.findByUsername(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            Map<RoleType, List<AuthRolePermissionDAO>> rolePermissionsMap = SetupDataLoader.rpMap.rpMap;
            System.out.println(rolePermissionsMap);
            for(Map.Entry<RoleType, List<AuthRolePermissionDAO>> entry : rolePermissionsMap.entrySet()) {
            	System.out.println("Key,val:" + entry.getKey() + " " + entry.getValue().size());
            }
            System.out.println(user.getAuthRole().getRoleType());
            List<AuthRolePermissionDAO> perms = rolePermissionsMap.get(user.getAuthRole().getRoleType());
            System.out.println("Size: " + perms.size());
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), /*user.isEnabled()*/true, true, true, isAccountNonLocked, getAuthorities(perms));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final Collection<? extends GrantedAuthority> getAuthorities(final List<AuthRolePermissionDAO> privileges) {
        return getGrantedAuthorities(privileges);
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<AuthRolePermissionDAO> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final AuthRolePermissionDAO privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getAuthPermission().getPermName()));
        }
        authorities.add(new SimpleGrantedAuthority("READ"));
        return authorities;
    }

    private final String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
