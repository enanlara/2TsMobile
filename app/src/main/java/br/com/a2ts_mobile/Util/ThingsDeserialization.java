package br.com.a2ts_mobile.Util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.a2ts_mobile.Things_Manager.ThingsModel;

/**
 * Created by Enan on 6/17/2017.
 */

public class ThingsDeserialization implements JsonDeserializer<ThingsModel> {
    @Override
    public ThingsModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement things = json.getAsJsonObject();
        if(json.getAsJsonObject().get("thing") != null){
            things = json.getAsJsonObject().get("thing");
        }

        return (new Gson().fromJson(things, ThingsModel.class));    }
}
