package com.danielandrej.treasure_hunt.models;

import javax.persistence.*;

@Entity
public class LocationHint {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id", nullable = false)
     private Long id;

    private double latitude;
    private double longitude;
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

    @Override
    public String toString() {
        return "LocationHint{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", radiusMeters=" + radiusMeters +
                '}';
    }
}
