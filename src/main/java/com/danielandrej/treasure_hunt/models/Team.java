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
    private Player leader;
    @OneToMany(mappedBy = "team")
    @NotNull
    @JsonBackReference
    private Set<@NotNull Player> players = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @NotNull
    private PlayerGameState playerGameState = new PlayerGameState();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<@NotNull Player> invitedPlayers = new HashSet<>();

    // players that have asked/requested to join the team
    @OneToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<@NotNull Player> pendingPlayers = new HashSet<>();

    public Team(String name, Player leader) {
        this.name = name;
        this.leader = leader;
        this.players.add(leader);
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

    public Player getLeader() {
        return leader;
    }

    public Team setLeader(Player leader) {
        this.leader = leader;
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
                ", leader=" + leader +
                ", players=" + players +
                ", playerGameState=" + playerGameState +
                '}';
    }

    public Set<Player> getInvitedPlayers() {
        return invitedPlayers;
    }

    public Team setInvitedPlayers(Set<Player> invitedPlayers) {
        this.invitedPlayers = invitedPlayers;
        return this;
    }

    public Team addInvitedPlayer(Player player) {
        this.invitedPlayers.add(player);
        return this;
    }

    public Set<Player> getPendingPlayers() {
        return pendingPlayers;
    }

    public Team setPendingPlayers(Set<Player> pendingPlayers) {
        this.pendingPlayers = pendingPlayers;
        return this;
    }
}
