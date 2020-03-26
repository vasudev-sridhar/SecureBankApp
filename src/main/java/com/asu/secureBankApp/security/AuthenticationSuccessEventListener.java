package com.asu.secureBankApp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	ActiveUserStore activeUserStore;

	private final int maxInactiveInterval = 30 * 60; // seconds

	@Override
	public void onApplicationEvent(final AuthenticationSuccessEvent e) {
		System.out.println("In AuthenticationSuccessEventListener");
		final WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
		if (auth != null) {
			loginAttemptService.loginSucceeded(auth.getRemoteAddress());
		}

		final HttpSession session = request.getSession(false);
		if (session != null) {
			session.setMaxInactiveInterval(maxInactiveInterval);
			String username;
			Authentication authentication = e.getAuthentication();
			if (authentication.getPrincipal() instanceof User) {
				username = (String) e.getAuthentication().getPrincipal();
			} else {
				username = authentication.getName();
			}

			LoggedUser user = new LoggedUser(username, activeUserStore);
			session.setAttribute("user", user);
		}
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			loginAttemptService.loginSucceeded(request.getRemoteAddr());
		} else {
			loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
		}
	}

}
