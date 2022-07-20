package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Team name is required")
    @Size(min = 3, max = 20, message = "Team name must be between 3 and 20 characters long")
    private String name;
    @OneToOne(optional = false)
    @NotNull
    @JsonBackReference
    private Player admin;
    @OneToMany
    @NotNull
    @JsonBackReference
    private Set<@NotNull Player> players = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @NotNull
    private PlayerGameState playerGameState = new PlayerGameState();

    public Team(String name, Player admin) {
        this.name = name;
        this.admin = admin;
        this.players.add(admin);
    }

    public Team() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Team setPlayers(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Set<Player> addPlayer(Player player) {
        this.players.add(player);
        return this.players;
    }

    public Player getAdmin() {
        return admin;
    }

    public Team setAdmin(Player admin) {
        this.admin = admin;
        return this;
    }

    public PlayerGameState getPlayerGameState() {
        return playerGameState;
    }

    public Team setPlayerGameState(PlayerGameState playerGameState) {
        this.playerGameState = playerGameState;
        return this;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", admin=" + admin +
                ", players=" + players +
                ", playerGameState=" + playerGameState +
                '}';
    }
}
