package com.zahid.models;

public class LocationData {

    private String state;
    private String country;
    private int latestTotalCases;

    public LocationData() {
    }

    public LocationData(String state, String country, int latestTotalCases) {
        this.state = state;
        this.country = country;
        this.latestTotalCases = latestTotalCases;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getLatestTotalCases() {
        return latestTotalCases;
    }
    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationData [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases + "]";
    }
    
}
