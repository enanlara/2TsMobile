package br.com.a2ts_mobile.Util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by enan on 23/09/17.
 */

public class StringDeserialization implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement response = json.getAsJsonObject();
        if (json.getAsJsonObject().get("response") != null) {
            response = json.getAsJsonObject().get("response");
        }
        return (new Gson().fromJson(response, String.class));
    }
}