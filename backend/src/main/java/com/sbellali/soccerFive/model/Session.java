package com.sbellali.soccerFive.model;

import java.time.LocalDateTime;
import java.util.List;

import com.sbellali.soccerFive.validator.SessionDuration.ValidSessionDuration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
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

    @ValidSessionDuration
    private double duration = 1.5;
    private String location;

    @Max(12)
    private int maxPlayers;
    private double price;
    private String description;

    @ManyToOne
    private User organizer;

    @ManyToMany
    @JoinTable(name = "session_players_junction", joinColumns = @JoinColumn(name = "session_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<User> players;

    public void addPlayer(User player) {
        if (!isPlayerInList(player)) {
            this.players.add(player);
        }
    }

    public boolean isPlayerInList(User player) {
        return this.players.stream().anyMatch(p -> p.getId().equals(player.getId()));
    }

}
