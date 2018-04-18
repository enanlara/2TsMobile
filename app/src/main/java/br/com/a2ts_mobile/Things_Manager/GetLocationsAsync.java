package br.com.a2ts_mobile.Things_Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.a2ts_mobile.Url;
import br.com.a2ts_mobile.User_Manager.UserModel;
import br.com.a2ts_mobile.Util.*;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Enan on 6/17/2017.
 */

public class GetLocationsAsync extends AsyncTask<Void, Void, List<LocationModel>>  {
    private Context context;
    public ProgressDialog dialog;
    public onResponseRetrofitListnnerLocations listnner;


    public GetLocationsAsync(Context context, onResponseRetrofitListnnerLocations listnner) {
        this.context = context;
        this.listnner = listnner;
    }


    @Override
    protected List<LocationModel> doInBackground(Void... params) {
        Call<List<LocationModel>> listLocation = null;
        try {


            String baseUrl = Url.UrlDeACesso;

            Gson gsonConverter = new GsonBuilder().registerTypeAdapter(LocationModel.class, new br.com.a2ts_mobile.Util.LocationDeserialization())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                    .build();

            final ThingsService services = retrofit.create(ThingsService.class);
            listLocation = services.getAllLocations(UserModel.TOKEN);


            List<LocationModel> listThingsResponse = listLocation.execute().body();

            return listThingsResponse;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Searching data", "Wait...", true, true );
        dialog.setCancelable(false);
    }

    @Override
    protected void onPostExecute(List<LocationModel> locations) {
        listnner.responseLocations(locations);
        dialog.dismiss();
    }

//    public interface onResponseRetrofitListnner{
//        public void responseLocations(List<LocationModel> response);
//    }

}
