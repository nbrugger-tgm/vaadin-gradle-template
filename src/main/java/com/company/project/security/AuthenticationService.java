package com.company.project.security;

import com.company.project.data.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	public User getAuthenticatedUser() {
		SecurityContext context   = SecurityContextHolder.getContext();
		Object          principal = context.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		}
		return null;
	}

	public void logout() {
		UI.getCurrent().getPage().setLocation(SecurityConfiguration.LOGOUT_URL);
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
	}

}
