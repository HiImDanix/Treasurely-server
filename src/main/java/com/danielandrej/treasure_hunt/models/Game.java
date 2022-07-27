package com.danielandrej.treasure_hunt.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Game {

    /* Status of the game */
    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        PAUSED,
        FINISHED
    }

    @Id
    @SequenceGenerator(
            name = "room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_sequence"
    )
    private long id;
    @Column(nullable = false, unique = true)
    @NotEmpty
    private String code;
    @Column(nullable = false)
    @NotEmpty
    @Size(min = 3, max = 32)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "game")
    @NotNull
    private Set<@NotNull Mission> missions = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "game")
    @NotNull
    @JsonManagedReference
    private Set<Player> players = new HashSet<>();
    @NotNull
    private Status status = Status.NOT_STARTED;

    public Game(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public Game() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", missions=" + missions +
                ", players=" + players +
                ", status=" + status +
                '}';
    }

    public Set<Mission> getMissions() {
        return missions;
    }

    public Game setMissions(Set<Mission> missions) {
        this.missions.retainAll(missions);
        this.missions.addAll(missions);
        return this;
    }

    public Game addMission(Mission mission) {
        this.missions.add(mission);
        return this;
    }

    public long getId() {
        return id;
    }

    public Game setId(long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Game setCode(String joinCode) {
        this.code = joinCode;
        return this;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Game setPlayers(Set<Player> players) {
        this.players.retainAll(players);
        this.players.addAll(players);
        return this;
    }

    public Game addPlayer(Player player) {
        this.players.add(player);
        return this;
    }

    public String getName() {
        return name;
    }

    public Game setName(String name) {
        this.name = name;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Game setStatus(Status status) {
        this.status = status;
        return this;
    }
}
