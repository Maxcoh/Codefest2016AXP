package com.cleanwater.axp.cleanwaterphilly;

/**
 * Created by brendanbarnes on 2/20/16.
 */
public class LatLong {
    private double lat;
    private double lon;

    public LatLong(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
