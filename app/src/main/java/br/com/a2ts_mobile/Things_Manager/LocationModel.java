package br.com.a2ts_mobile.Things_Manager;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LocationModel implements Serializable{
    public static List<LocationModel> listLocations;

    @SerializedName("loca_id")
    private Integer id;
    @SerializedName("loca_room")
    private String room;

    public LocationModel() {
    }

    public LocationModel(Integer id, String room) {
        this.id = id;
        this.room = room;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return room;
    }
}
