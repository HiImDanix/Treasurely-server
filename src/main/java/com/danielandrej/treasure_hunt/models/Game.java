package com.danielandrej.treasure_hunt.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
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
}
