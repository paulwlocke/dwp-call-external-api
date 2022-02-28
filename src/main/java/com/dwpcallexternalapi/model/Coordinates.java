package com.dwpcallexternalapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {

    @JsonProperty
    private Double latitude;

    @JsonProperty
    private Double longitude;

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * A boolean predicate returning true if the provided Coordinates object is within the provided number of miles
     * determined by longitude and latitude.
     * The calculation is a simple approximation based upon assumptions of the earth's circumference and shape.
     * @param from a Coordinates object
     * @param miles a distance in miles
     * @return true or false
     */
    public Boolean isWithinDistanceOf( Coordinates from, Double miles ) {
        Double milesPerDegree = ( 24000 / 360.00);
        Double numberOfDegrees = miles / milesPerDegree;
        return Math.abs( from.latitude - latitude ) <= numberOfDegrees && Math.abs( from.longitude - longitude ) <= numberOfDegrees;
    }
}
