package com.sbellali.soccerFive;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sbellali.soccerFive.model.ApplicationUser;
import com.sbellali.soccerFive.model.Role;
import com.sbellali.soccerFive.repository.RoleRepository;
import com.sbellali.soccerFive.repository.UserRepository;

@SpringBootApplication
public class SoccerFiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccerFiveApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {

			if (roleRepository.findByAuthority("ADMIN").isPresent())
				return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin = new ApplicationUser(1, "admin", passwordEncoder.encode("password"), roles);
			userRepository.save(admin);
		};
	}

}
