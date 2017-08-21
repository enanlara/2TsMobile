package br.com.a2ts_mobile.Things_Manager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Enan on 6/17/2017.
 */

public class ThingsModel implements Serializable  {
    @SerializedName("code_things")
    @Expose
    private Integer codeThings;
    @SerializedName("nr_things1")
    @Expose
    private Integer nrThings1;
    @SerializedName("nr_things2")
    @Expose
    private Integer nrThings2;
    private String description;
    private String situation;
    private Double value;
    @SerializedName("date_registre")
    @Expose
    private String dateRegistre;
    private String state;
    private LocationModel location;
    private String note;
    @SerializedName("tag_activated")
    @Expose
    private Integer tagActivated;
    @SerializedName("location_current")
    @Expose
    private LocationModel locationCurrent;

    public Integer getCodeThings() {
        return codeThings;
    }

    public void setCodeThings(Integer codeThings) {
        this.codeThings = codeThings;
    }

    public Integer getNrThings1() {
        return nrThings1;
    }

    public void setNrThings1(Integer nrThings1) {
        this.nrThings1 = nrThings1;
    }

    public Integer getNrThings2() {
        return nrThings2;
    }

    public void setNrThings2(Integer nrThings2) {
        this.nrThings2 = nrThings2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDateRegistre() {
        return dateRegistre;
    }

    public void setDateRegistre(String dateRegistre) {
        this.dateRegistre = dateRegistre;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTagActivated() {
        return tagActivated;
    }

    public void setTagActivated(Integer tagActivated) {
        this.tagActivated = tagActivated;
    }

    public LocationModel getLocationCurrent() {
        return locationCurrent;
    }

    public void setLocationCurrent(LocationModel locationCurrent) {
        this.locationCurrent = locationCurrent;
    }

    @Override
    public String toString() {
        return "ThingsModel{" +
                "nrThings1=" + nrThings1 +
                ", description='" + description + '\'' +
                '}';
    }
}
