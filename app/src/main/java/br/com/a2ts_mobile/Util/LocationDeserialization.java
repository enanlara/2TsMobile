package br.com.a2ts_mobile.Util;

/**
 * Created by enan on 23/09/17.
 */


import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.a2ts_mobile.Things_Manager.LocationModel;

public class LocationDeserialization implements JsonDeserializer<LocationModel> {
    @Override
    public LocationModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement locations = json.getAsJsonObject();
        if(json.getAsJsonObject().get("locations") != null){
            locations = json.getAsJsonObject().get("locations");
        }
        return (new Gson().fromJson(locations, LocationModel.class));    }
}
