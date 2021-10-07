package com.company.project.security.impl;

import com.company.project.data.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
		ElementType.METHOD,
		ElementType.TYPE
})
public @interface RequiresPermission {
	Permission[] value();
}
