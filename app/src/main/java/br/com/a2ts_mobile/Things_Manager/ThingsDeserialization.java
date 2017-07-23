package br.com.a2ts_mobile.Things_Manager;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Enan on 6/17/2017.
 */

public class ThingsDeserialization implements JsonDeserializer<ThingsModel> {
    @Override
    public ThingsModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement things = json.getAsJsonObject();
        if(json.getAsJsonObject().get("things") != null){
            things = json.getAsJsonObject().get("things");
        }
        return (new Gson().fromJson(things, ThingsModel.class));    }
}

class LocationDeserialization implements JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement locations = json.getAsJsonObject();
        if(json.getAsJsonObject().get("locations") != null){
            locations = json.getAsJsonObject().get("locations");
        }
        return (new Gson().fromJson(locations, Location.class));    }
}
