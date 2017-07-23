package br.com.a2ts_mobile.User_Manager;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Enan on 6/17/2017.
 */

public class UserDeserialization implements JsonDeserializer<UserModel> {
    @Override
    public UserModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement bens = json.getAsJsonObject();

        if(json.getAsJsonObject().get("user") != null){
            bens = json.getAsJsonObject();
        }
        return (new Gson().fromJson(bens, UserModel.class));
    }
}
