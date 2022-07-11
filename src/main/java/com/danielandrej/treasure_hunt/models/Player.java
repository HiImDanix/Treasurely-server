package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String playerSessionID;
    private String name;
    @ManyToOne
    @JsonBackReference
    private Game game;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Task> completedTasks = new HashSet<>();

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
        return Objects.equals(playerSessionID, player.playerSessionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerSessionID);
    }

    @Override
    public String toString() {
        return "Player{" +
                "sessionID='" + playerSessionID + '\'' +
                ", name='" + name + '\'' +
                ", game=" + game +
                ", completedTasks=" + completedTasks +
                '}';
    }

    public String getPlayerSessionID() {
        return playerSessionID;
    }

    public Player setPlayerSessionID(String sessionID) {
        this.playerSessionID = sessionID;
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
