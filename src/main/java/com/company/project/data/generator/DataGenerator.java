package com.company.project.data.generator;

import com.company.project.data.Permission;
import com.company.project.data.service.UserRepository;
import com.company.project.data.entity.User;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class DataGenerator {

	@Bean
	public CommandLineRunner loadData(
			PasswordEncoder passwordEncoder,
			UserRepository userRepository,
			@Value("${niton.cloud.admin}") String adminDefaultName
	) {
		return args -> {
			Logger logger = LoggerFactory.getLogger(getClass());
			if (userRepository.count() != 0L) {
				logger.info("Using existing database");
				return;
			}
			logger.info("Generate Admin user : {} Password : {}", adminDefaultName, "root");
			User user = new User();
			user.setDisplayName("Root");
			user.setUsername(adminDefaultName);
			user.setHashedPassword(passwordEncoder.encode("root"));
			user.setProfilePictureUrl(
					"https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
			user.setPermissions(Permission.ROOT);
			userRepository.save(user);
		};
	}
}