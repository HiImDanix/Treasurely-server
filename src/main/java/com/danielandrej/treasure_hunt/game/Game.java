package com.danielandrej.treasure_hunt.game;


import com.danielandrej.treasure_hunt.player.Player;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Game {
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private Set<String> answers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "game")
    @JsonManagedReference
    private Set<Player> players;

    public Game(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Game() {
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public Game setAnswers(Set<String> answers) {
        this.answers = answers;
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
        this.players = players;
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
}
