package com.company.project.data.entity;

import com.company.project.data.AbstractEntity;
import com.company.project.data.Permission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User extends AbstractEntity implements UserDetails {
	@Unique
	@NotNull
	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9_\\-#]+")
	@Size(max = 32)
	private String username;

	@Size(max = 32)
	@NotBlank
	private String displayName;

	@Email
	private String email;

	@JsonIgnore
	private String hashedPassword;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Permission> permissions;

	@Lob
	private String profilePictureUrl;

	@NotNull
	private boolean deleted = false;

	@NotNull
	private boolean disabled = false;

	public String getDisplayName() {
		if (displayName == null)
			return username;
		return displayName;
	}

	public void setPermissions(Permission... permissions) {
		this.permissions = Set.of(permissions);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return permissions;
	}

	@Override
	public String getPassword() {
		return hashedPassword;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !disabled;
	}

	public boolean hasPermissions(Permission... required) {
		for (Permission requiredPermission : required) {
			if (!hasPermission(requiredPermission)) return false;
		}
		return true;
	}

	private boolean hasPermission(Permission required) {
		for (Permission permission : permissions) {
			if(permission.provides(required))
				return true;
		}
		return false;
	}
}

