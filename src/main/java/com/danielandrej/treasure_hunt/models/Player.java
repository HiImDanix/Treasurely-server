package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    private String sessionID;
    @NotBlank(message = "Player name is required")
    @Size(min = 3, max = 20)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
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

    @PrePersist
    protected void onCreate() {
        this.sessionID = java.util.UUID.randomUUID().toString();
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

    public Long getId() {
        return id;
    }

    public Player setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", sessionID='" + sessionID + '\'' +
                ", name='" + name + '\'' +
                ", team=" + team +
                ", game=" + game +
                ", playerGameState=" + playerGameState +
                '}';
    }
}
