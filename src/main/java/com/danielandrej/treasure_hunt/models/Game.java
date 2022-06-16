package com.danielandrej.treasure_hunt.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
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
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "game")
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "game")
    @JsonManagedReference
    private Set<Player> players = new HashSet<>();

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
                ", tasks=" + tasks +
                ", players=" + players +
                '}';
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Game setTasks(Set<Task> tasks) {
        this.tasks.retainAll(tasks);
        this.tasks.addAll(tasks);
        return this;
    }

    public Game addTask(Task task) {
        this.tasks.add(task);
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
