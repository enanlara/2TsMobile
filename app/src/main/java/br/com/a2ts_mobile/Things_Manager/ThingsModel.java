package br.com.a2ts_mobile.Things_Manager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Enan on 6/17/2017.
 */

public class ThingsModel implements Serializable  {
    @SerializedName("cod_bem")
    @Expose
    private String codeThing;
    @SerializedName("descricao")
    @Expose
    private String description;
    @SerializedName("estado")
    @Expose
    private String state;
    @SerializedName("localizacao")
    @Expose
    private String location;
    @SerializedName("situacao")
    @Expose
    private String situation;

    public String getCodeThing() {
        return codeThing;
    }

    public void setCodeThing(String codeThing) {
        this.codeThing = codeThing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    @Override
    public String toString() {
        return "ThingsModel{" +
                "codeThing='" + codeThing + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", location='" + location + '\'' +
                ", situation='" + situation + '\'' +
                '}';
    }
}
