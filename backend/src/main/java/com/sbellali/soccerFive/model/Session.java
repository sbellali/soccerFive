package com.sbellali.soccerFive.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private int maxParticipants;
    private double price;
    private String description;

    @ManyToOne
    private User organizer;

    @ManyToMany
    @JoinTable(name = "session_players_junction", joinColumns = @JoinColumn(name = "session_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<User> players;

}
