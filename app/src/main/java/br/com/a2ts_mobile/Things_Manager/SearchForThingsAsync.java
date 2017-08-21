package br.com.a2ts_mobile.Things_Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.a2ts_mobile.User_Manager.UserModel;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Enan on 6/17/2017.
 */

public class SearchForThingsAsync extends AsyncTask<String, Void, List<ThingsModel>>  {
    private Context context;
    public ProgressDialog dialog;
    public SearchForThingsAsync.onResponseRetrofitListnner listnner;


    public SearchForThingsAsync(Context context, SearchForThingsAsync.onResponseRetrofitListnner listnner) {
        this.context = context;
        this.listnner = listnner;
    }


    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Carregando dados", "Aguarde", true, true );
    }

    @Override
    protected List<ThingsModel> doInBackground(String... params) {
        String dataSearch = params[0];
        String typeSearch = params[1];

        if (typeSearch.equals("1")) {
            return getThingsByLocation(dataSearch);
        }else if(typeSearch.equals("2")){
            return getThingsOverByLocation(dataSearch);
        }else if(typeSearch.equals("3")){
            return getThingsMissingByLocation(dataSearch);
        }else if(typeSearch.equals("4")){
            return getThingsByNum(dataSearch);
        }
        return null;
    }

    private List<ThingsModel> getThingsByLocation(String locaId){
        try {
            Call<List<ThingsModel>> listThingsService = null;

            final ThingsService services = createServiceRetrofit();
            if(services != null) {
                listThingsService = services.getThingByLocation(UserModel.TOKEN, locaId);
                List<ThingsModel> listThingsResponse = listThingsService.execute().body();

                return listThingsResponse;
            }
            return null;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }

    private List<ThingsModel> getThingsOverByLocation(String locaId){
        try {
            Call<List<ThingsModel>> listThingsService = null;

            final ThingsService services = createServiceRetrofit();
            if(services != null) {
                listThingsService = services.getThingsOverByLocation(UserModel.TOKEN, locaId);
                List<ThingsModel> listThingsResponse = listThingsService.execute().body();

                return listThingsResponse;
            }
            return null;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }
    private List<ThingsModel> getThingsMissingByLocation(String locaId){
        try {
            Call<List<ThingsModel>> listThingsService = null;

            final ThingsService services = createServiceRetrofit();
            if(services != null) {
                listThingsService = services.getThingsMissingByLocation(UserModel.TOKEN, locaId);
                List<ThingsModel> listThingsResponse = listThingsService.execute().body();

                return listThingsResponse;
            }
            return null;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }
    private List<ThingsModel> getThingsByNum(String num){
        try {
            Call<ThingsModel> thingsService = null;

            final ThingsService services = createServiceRetrofit();
            if(services != null) {
                thingsService = services.getThingByNum(UserModel.TOKEN, num);
                ThingsModel thingsResponse = thingsService.execute().body();
                List<ThingsModel> listThings = new ArrayList<>();
                listThings.add(thingsResponse);
                return listThings;
            }
            return null;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }
    private ThingsService createServiceRetrofit(){
        try {
            String baseUrl = "https://dg-2ts-server.herokuapp.com/";

            Gson gsonConverter = new GsonBuilder().registerTypeAdapter(ThingsModel.class, new ThingsDeserialization())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                    .build();

            return retrofit.create(ThingsService.class);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ThingsModel> things) {
        listnner.responseThings(things);
        dialog.dismiss();
    }

    public interface onResponseRetrofitListnner{
        public void responseThings(List<ThingsModel> response);
    }

}
