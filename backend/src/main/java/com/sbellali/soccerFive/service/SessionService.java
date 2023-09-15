package com.sbellali.soccerFive.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbellali.soccerFive.dto.SessionRequestDTO;
import com.sbellali.soccerFive.dto.SessionDTO;
import com.sbellali.soccerFive.exception.MaxPlayersReachedException;
import com.sbellali.soccerFive.exception.SessionNotFoundException;
import com.sbellali.soccerFive.exception.UnauthorizedActionException;
import com.sbellali.soccerFive.model.Session;
import com.sbellali.soccerFive.model.User;
import com.sbellali.soccerFive.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SessionDTO> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(session -> this.sessionToDTO(session))
                .toList();
    }

    public List<SessionDTO> getSessionsByDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime startOfDay = LocalDate.parse(date, formatter).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date, formatter).atTime(LocalTime.MAX);
        return sessionRepository.findByStartTimeBetween(startOfDay, endOfDay).stream()
                .map(session -> this.sessionToDTO(session))
                .toList();

    }

    public SessionDTO getSession(Integer id) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException());
        return this.sessionToDTO(session);
    }

    public SessionDTO createSession(SessionRequestDTO sessionCreateRequest, User organizer) {
        Session newSession = modelMapper.map(sessionCreateRequest, Session.class);
        newSession.setOrganizer(organizer);
        newSession.setPlayers(new ArrayList<User>());
        return this.saveAndGetDtoSession(newSession);
    }

    public SessionDTO modifySession(int id, SessionRequestDTO sessionCreateRequest, User organizer) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException());
        if (!this.isOrganizerofSession(session, organizer)) {
            throw new UnauthorizedActionException();
        }
        modelMapper.map(sessionCreateRequest, session);
        return this.saveAndGetDtoSession(session);
    }

    public void deleteSession(int id, User organizer) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException());
        if (!this.isOrganizerofSession(session, organizer)) {
            throw new UnauthorizedActionException();
        }

        sessionRepository.deleteById(id);
    }

    public SessionDTO addPlayerToSession(int id, User player) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException());
        if (this.hasReachedMaxPlayers(session)) {
            throw new MaxPlayersReachedException();
        }
        session.addPlayer(player);
        return this.saveAndGetDtoSession(session);
    }

    public boolean isOrganizerofSession(Session session, User organizer) {
        Integer sessionOrganizerId = session.getOrganizer().getId();
        Integer organizerId = organizer.getId();

        return sessionOrganizerId.equals(organizerId);
    }

    public boolean hasReachedMaxPlayers(Session session) {
        return session.getPlayers().size() > session.getMaxPlayers();
    }

    private SessionDTO saveAndGetDtoSession(Session session) {
        Session savedSession = sessionRepository.save(session);
        return this.sessionToDTO(savedSession);
    }

    private SessionDTO sessionToDTO(Session session) {
        return modelMapper.map(session, SessionDTO.class);
    }

}
