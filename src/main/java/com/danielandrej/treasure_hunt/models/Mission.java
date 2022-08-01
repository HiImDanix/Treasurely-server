package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;


@Entity
@Getter @Setter @NoArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    @NotEmpty
    private String qrCodeValue;
    @JsonBackReference
    @ManyToOne
    @NotNull
    private Game game;
    @NotEmpty(message = "Mission description is required")
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    private LocationHint locationHint;
    @Column(nullable = false)
    @Positive(message = "Points earned for completing a mission must be positive")
    private int points;


    public Mission(Game game, String description, int points, LocationHint locationHint, String qrCodeValue) {
        this.game = game;
        this.description = description;
        this.points = points;
        this.locationHint = locationHint;
        this.qrCodeValue = qrCodeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return Objects.equals(id, mission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", qrCodeValue='" + qrCodeValue + '\'' +
                ", game=" + game +
                ", description='" + description + '\'' +
                ", locationHint=" + locationHint +
                ", points=" + points +
                '}';
    }
}