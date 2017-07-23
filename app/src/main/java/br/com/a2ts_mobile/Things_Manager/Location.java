package br.com.a2ts_mobile.Things_Manager;

import com.google.gson.annotations.SerializedName;

public class Location{
    @SerializedName("location")
    private String Location;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
