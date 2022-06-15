package com.danielandrej.treasure_hunt.player;

import com.danielandrej.treasure_hunt.game.Game;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

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

    @ElementCollection
    private List<String> answers;

    public Player(String sessionID, String name, Game game) {
        this.sessionID = sessionID;
        this.name = name;
        this.game = game;
    }

    public Player() {

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

    public List<String> getAnswers() {
        return answers;
    }

    public Player setAnswers(List<String> answers) {
        this.answers = answers;
        return this;
    }

    public Player addAnswer(String answer) {
        answers.add(answer);
        return this;
    }
}
