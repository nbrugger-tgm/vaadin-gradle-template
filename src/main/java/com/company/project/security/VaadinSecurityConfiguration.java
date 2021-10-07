package com.company.project.security;

import com.company.project.security.impl.PermissionAnnotationChecker;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.ViewAccessChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class VaadinSecurityConfiguration {
	@Primary
	@Bean
	public AccessAnnotationChecker customAccessAnnotationChecker() {
		return new PermissionAnnotationChecker();
	}

	@Primary
	@Bean
	public ViewAccessChecker customViewAccessChecker() {
		return new CustomViewAccessChecker();
	}
	class CustomViewAccessChecker extends ViewAccessChecker{
		public CustomViewAccessChecker(){
			super(customAccessAnnotationChecker());
		}
	}
}
