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

    public Mission(Game game, String qrCodeValue) {
        this.game = game;
        this.qrCodeValue = qrCodeValue;
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
}