package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
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

}
