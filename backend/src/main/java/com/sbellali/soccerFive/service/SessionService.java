package com.sbellali.soccerFive.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbellali.soccerFive.model.Session;
import com.sbellali.soccerFive.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public List<Session> getSessionsByDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime startOfDay = LocalDate.parse(date, formatter).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date, formatter).atTime(LocalTime.MAX);
        return sessionRepository.findByStartTimeBetween(startOfDay, endOfDay);
    }

}
