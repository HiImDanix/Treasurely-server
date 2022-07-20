package com.danielandrej.treasure_hunt.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PlayerGameState {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OneToMany
    protected Set<Mission> completedMissions = new HashSet<>();

    public PlayerGameState() {
    }

    public Set<Mission> getCompletedMissions() {
        return completedMissions;
    }

    public PlayerGameState setCompletedMissions(Set<Mission> completedMissions) {
        this.completedMissions = completedMissions;
        return this;
    }

    public PlayerGameState addCompletedMission(Mission mission) {
        this.completedMissions.add(mission);
        return this;
    }

    public Long getId() {
        return id;
    }

    public PlayerGameState setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "PlayerGameState{" +
                "id=" + id +
                ", completedMissions=" + completedMissions +
                '}';
    }
}
