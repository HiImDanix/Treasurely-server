package com.danielandrej.treasure_hunt.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class PlayerGameState {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OneToMany
    @NotNull
    protected Set<Mission> completedMissions = new HashSet<>();

    @Override
    public String toString() {
        return "PlayerGameState{" +
                "id=" + id +
                ", completedMissions=" + completedMissions +
                '}';
    }
}
