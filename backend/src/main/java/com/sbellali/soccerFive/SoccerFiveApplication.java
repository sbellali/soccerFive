package com.sbellali.soccerFive;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sbellali.soccerFive.model.User;
import com.sbellali.soccerFive.model.Role;
import com.sbellali.soccerFive.model.Session;
import com.sbellali.soccerFive.repository.RoleRepository;
import com.sbellali.soccerFive.repository.SessionRepository;
import com.sbellali.soccerFive.repository.UserRepository;

@SpringBootApplication
public class SoccerFiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccerFiveApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner run(
			RoleRepository roleRepository,
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			SessionRepository sessionRepository) {
		return args -> {
			this.createUsers(roleRepository, userRepository, passwordEncoder);
			this.createSession(sessionRepository, userRepository);
		};
	}

	private void createUsers(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		if (roleRepository.findByAuthority("ADMIN").isPresent())
			return;
		Role adminRole = roleRepository.save(new Role("ADMIN"));
		roleRepository.save(new Role("USER"));
		Set<Role> roles = new HashSet<>();
		roles.add(adminRole);

		User admin = new User("admin", "admin@soccerFive.com", passwordEncoder.encode("password"), roles);
		userRepository.save(admin);
	}

	private void createSession(SessionRepository sessionRepository, UserRepository userRepository) {
		User organizer = userRepository.findById(1).get();
		Session session1 = new Session();
		List<User> players = new ArrayList<>();

		session1.setStartTime(LocalDateTime.now());
		session1.setDuration(2.0);
		session1.setLocation("Soccer Field 1");
		session1.setMaxPlayers(10);
		session1.setPrice(20.0);
		session1.setDescription("Soccer practice session");
		session1.setOrganizer(organizer);
		session1.setPlayers(players);

		sessionRepository.save(session1);

		Session session2 = new Session();
		session2.setStartTime(LocalDateTime.now().plusDays(1));
		session2.setDuration(1.5);
		session2.setLocation("Soccer Field 2");
		session2.setMaxPlayers(12);
		session2.setPrice(25.0);
		session2.setDescription("Friendly soccer match");
		session2.setOrganizer(organizer);
		session2.setPlayers(players);
		sessionRepository.save(session2);
	}

}
