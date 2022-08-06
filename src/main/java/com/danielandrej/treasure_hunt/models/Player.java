package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    private String sessionID;
    @NotBlank(message = "Player name is required")
    @Size(min = 3, max = 20)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private Team team;
    @ManyToOne
    @NotNull
    private Game game;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @NotNull
    private PlayerGameState playerGameState = new PlayerGameState();
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    @PrePersist
    protected void onCreate() {
        this.sessionID = java.util.UUID.randomUUID().toString();
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

    public void setTeam(Team team) {
        this.team = team;
        team.getPlayers().add(this);
    }

}
