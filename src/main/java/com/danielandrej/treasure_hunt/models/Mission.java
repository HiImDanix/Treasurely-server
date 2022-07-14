package com.danielandrej.treasure_hunt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String qrCodeValue;

    @JsonBackReference
    @ManyToOne
    private Game game;

    @Column(nullable = true)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private LocationHint locationHint;


    public Mission(Game game, String qrCodeValue, String description, LocationHint locationHint) {
        this.game = game;
        this.qrCodeValue = qrCodeValue;
        this.locationHint = locationHint;
        this.description = description;
    }

    public Mission() {
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
                ", locationHint=" + locationHint +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQrCodeValue() {
        return qrCodeValue;
    }

    public Mission setQrCodeValue(String qrCodeValue) {
        this.qrCodeValue = qrCodeValue;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public Mission setGame(Game game) {
        this.game = game;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationHint getLocationHint() {
        return locationHint;
    }

    public void setLocationHint(LocationHint locationHint) {
        this.locationHint = locationHint;
    }


}