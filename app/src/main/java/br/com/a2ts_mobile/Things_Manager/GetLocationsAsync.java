package br.com.a2ts_mobile.Things_Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Enan on 6/17/2017.
 */

public class GetLocationsAsync extends AsyncTask<Void, Void, List<Location>>  {
    private Context context;
    public ProgressDialog dialog;
    public GetLocationsAsync.onResponseRetrofitListnner listnner;


    public GetLocationsAsync(Context context, GetLocationsAsync.onResponseRetrofitListnner listnner) {
        this.context = context;
        this.listnner = listnner;
    }


    @Override
    protected List<Location> doInBackground(Void... params) {
        Call<List<Location>> listLocation = null;
        try {


            String baseUrl = "https://my-project-1-171803.appspot.com/";

            Gson gsonConverter = new GsonBuilder().registerTypeAdapter(Location.class, new LocationDeserialization())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                    .build();

            final ThingsService services = retrofit.create(ThingsService.class);
            listLocation = services.getAllLocations();


            List<Location> listThingsResponse = listLocation.execute().body();
            Log.i("----------------", String.valueOf(listThingsResponse.size()));

            return listThingsResponse;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Carregando dados", "Aguarde", true, true );
    }

    @Override
    protected void onPostExecute(List<Location> locations) {
        listnner.responseLocations(locations);
        dialog.dismiss();
    }

    public interface onResponseRetrofitListnner{
        public void responseLocations(List<Location> response);
    }

}
