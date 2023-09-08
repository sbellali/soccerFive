package com.sbellali.soccerFive.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbellali.soccerFive.model.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    public List<Session> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
