package com.company.project.security;

import com.vaadin.flow.server.auth.AccessAnnotationChecker;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.function.Function;

public abstract class CustomAccessChecker extends AccessAnnotationChecker {
	@Override
	public boolean hasAccess(
			Method method, Principal principal, Function<String, Boolean> roleChecker
	) {
		return checkAccess(this.getSecurityTarget(method),principal,roleChecker);
	}

	@Override
	public boolean hasAccess(
			Class<?> cls, Principal principal, Function<String, Boolean> roleChecker
	) {
		return checkAccess(this.getSecurityTarget(cls),principal,roleChecker);
	}

	protected abstract boolean checkAccess(AnnotatedElement securityTarget, Principal principal, Function<String, Boolean> roleChecker);
}
