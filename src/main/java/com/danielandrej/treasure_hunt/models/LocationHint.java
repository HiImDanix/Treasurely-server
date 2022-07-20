package com.danielandrej.treasure_hunt.models;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
public class LocationHint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double latitude;
    private double longitude;
    @Positive
    private double radiusMeters;

    public LocationHint(double latitude, double longitude, double radiusMeters) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radiusMeters = radiusMeters;
    }

    public LocationHint() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadiusMeters() {
        return radiusMeters;
    }

    public void setRadiusMeters(double radiusMeters) {
        this.radiusMeters = radiusMeters;
    }

    public Long getId() {
        return id;
    }

    public LocationHint setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "LocationHint{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", radiusMeters=" + radiusMeters +
                '}';
    }
}
