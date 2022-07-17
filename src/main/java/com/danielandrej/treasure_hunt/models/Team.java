package com.danielandrej.treasure_hunt.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {

    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToOne(optional = false)
    private Player admin;
    @OneToMany
    private Set<Player> players;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private PlayerGameState playerGameState = new PlayerGameState();

    public Team(String name, Player admin) {
        this.name = name;
        this.admin = admin;
        this.players = new HashSet<>();
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
