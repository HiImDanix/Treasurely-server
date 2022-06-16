package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Player {

    @Id
    @Column(nullable = false)
    private String sessionID;
    private String name;
    @ManyToOne(cascade=CascadeType.ALL)
    @JsonBackReference
    private Game game;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Task> completedTasks = new HashSet<>();

    public Player(String sessionID, String name, Game game) {
        this.sessionID = sessionID;
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

    @Override
    public String toString() {
        return "Player{" +
                "sessionID='" + sessionID + '\'' +
                ", name='" + name + '\'' +
                ", game=" + game +
                ", completedTasks=" + completedTasks +
                '}';
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

    public Game getGame() {
        return game;
    }

    public Player setGame(Game game) {
        this.game = game;
        return this;
    }

    public Set<Task> getCompletedTasks() {
        return completedTasks;
    }

    public Player setCompletedTasks(Set<Task> completedTasks) {
        this.completedTasks.retainAll(completedTasks);
        this.completedTasks.addAll(completedTasks);
        return this;
    }

    public Player addCompletedTask(Task task) {
        completedTasks.add(task);
        return this;
    }
}
