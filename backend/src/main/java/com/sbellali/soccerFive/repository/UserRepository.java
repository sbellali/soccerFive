package com.sbellali.soccerFive.repository;

import com.sbellali.soccerFive.model.ApplicationUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
  Optional<ApplicationUser> findByUsername(String username);
}
