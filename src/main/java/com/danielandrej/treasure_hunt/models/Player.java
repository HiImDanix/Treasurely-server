package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Player {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String sessionID;
    @NotEmpty(message = "Player name is required")
    @Size(min = 3, max = 20)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "admin")
    private Team team;
    @ManyToOne
    @JsonBackReference
    @NotNull
    private Game game;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @NotNull
    private PlayerGameState playerGameState = new PlayerGameState();
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    public Player() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(sessionID, player.sessionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionID);
    }

    public String getSessionID() {
        return sessionID;
    }

    public Player setSessionID(String sessionID) {
        this.sessionID = sessionID;
        return this;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public Player setTeam(Team team) {
        this.team = team;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public Player setGame(Game game) {
        this.game = game;
        return this;
    }

    public PlayerGameState getPlayerGameState() {
        return playerGameState;
    }

    public Player setPlayerGameState(PlayerGameState playerGameState) {
        this.playerGameState = playerGameState;
        return this;
    }

    @Override
    public String toString() {
        return "Player{" +
                "sessionID='" + sessionID + '\'' +
                ", name='" + name + '\'' +
                ", team=" + team +
                ", game=" + game +
                ", playerGameState=" + playerGameState +
                '}';
    }
}
