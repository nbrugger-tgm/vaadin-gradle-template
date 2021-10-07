package com.company.project.security.impl;

import com.company.project.data.entity.User;
import com.company.project.security.CustomAccessChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.lang.reflect.AnnotatedElement;
import java.security.Principal;
import java.util.function.Function;

public class PermissionAnnotationChecker extends CustomAccessChecker {
	@Override
	protected boolean checkAccess(
			AnnotatedElement securityTarget, Principal principal, Function<String, Boolean> roleChecker
	) {
		if(securityTarget.isAnnotationPresent(Public.class))
			return true;
		if(securityTarget.isAnnotationPresent(RequiresPermission.class)){
			User user;
			if(principal == null)
				return false;
			if(principal instanceof User){
				user = (User) principal;
			}else if(principal instanceof UsernamePasswordAuthenticationToken){
				Object mUser = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
				if(mUser instanceof User)
					user = (User) mUser;
				else
					throw new RuntimeException(principal+" is not a user.");
			}else{
				throw new RuntimeException(principal+" is not a user.");
			}
			RequiresPermission permissions = securityTarget.getAnnotation(RequiresPermission.class);
			return user.hasPermissions(permissions.value());
		}
		return true;
	}
}
