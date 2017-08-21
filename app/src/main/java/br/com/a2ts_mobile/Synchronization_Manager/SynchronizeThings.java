package br.com.a2ts_mobile.Synchronization_Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.a2ts_mobile.Things_Manager.ThingsModel;
import br.com.a2ts_mobile.User_Manager.UserModel;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Enan on 6/17/2017.
 */

public class SynchronizeThings extends AsyncTask<Void, Void, String> {
    private Context context;
    public ProgressDialog dialog;
    public SynchronizeThings.onResponseRetrofitListnner listnner;
    private ThingsModel thingsModel;

    public SynchronizeThings(Context context, SynchronizeThings.onResponseRetrofitListnner listnner, ThingsModel thingsModel) {
        this.context = context;
        this.listnner = listnner;
        this.thingsModel = thingsModel;
    }
    @Override
    protected String doInBackground(Void... params) {
        return editThings();
    }

    private String editThings(){
        try {

            Call<String> listThingsService = null;

            String baseUrl = "https://dg-2ts-server.herokuapp.com/";

            Gson gsonConverter = new GsonBuilder().registerTypeAdapter(String.class, new StringDeserialization())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                    .build();

            final SynchronizationThingsService services = retrofit.create(SynchronizationThingsService.class);

             listThingsService = services.synchronizeThings(UserModel.TOKEN.trim(), thingsModel.getNrThings1().toString().trim(), thingsModel.getLocationCurrent().getId().toString().trim(), thingsModel.getSituation().trim(), thingsModel.getState().trim(), (thingsModel.getNote().isEmpty()?"None":thingsModel.getNote().trim()));


            String response = listThingsService.execute().body();
            Log.i("EXC----------------", response);

            return response;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }


    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Atualizando", "Aguarde", true, true );
    }
    @Override
    protected void onPostExecute(String response) {
        listnner.responseEditThing(response);
        dialog.dismiss();
    }

    public interface onResponseRetrofitListnner{
        public void responseEditThing(String response);
    }
}

class StringDeserialization implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement response = json.getAsJsonObject();
        if (json.getAsJsonObject().get("response") != null) {
            response = json.getAsJsonObject().get("response");
        }
        return (new Gson().fromJson(response, String.class));
    }

//    @Override
//    public String deserialize(JsonElement json, Type type,
//                               JsonDeserializationContext context) throws JsonParseException {
//
//        JsonObject jobject = json.getAsJsonObject();
//
//        return jobject.get("response").getAsString();
//    }
}