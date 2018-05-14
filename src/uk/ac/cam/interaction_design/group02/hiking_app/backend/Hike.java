package uk.ac.cam.interaction_design.group02.hiking_app.backend;

import java.io.Serializable;

/**
 * Mutable class representing a single hike
 */
public class Hike implements Serializable{
    private double longitude;
    private double latitude;

    private String name;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
