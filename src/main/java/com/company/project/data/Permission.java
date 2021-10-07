package com.company.project.data;

import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@FieldNameConstants
public enum Permission implements GrantedAuthority {
	//Add custom permissions
	MANAGE_USERS,
	DEFAULT_USER,
	ROOT(MANAGE_USERS);

	private final Set<Permission> inherited;

	Permission() {
		inherited = Set.of();
	}

	Permission(Permission... inherits) {
		inherited = Set.of(inherits);
	}

	@Override
	public String getAuthority() {
		return name();
	}

	public boolean provides(Permission permission) {
		return equals(permission) || inherited.contains(permission);
	}
}
