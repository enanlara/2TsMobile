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
        return  getThingsByLocation(params[0]);
    }

    private List<ThingsModel> getThingsByLocation(String location){
        try {
            Call<List<ThingsModel>> listThingsService = null;

            String baseUrl = "https://my-project-1-171803.appspot.com/";

            Gson gsonConverter = new GsonBuilder().registerTypeAdapter(ThingsModel.class, new ThingsDeserialization())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                    .build();

            final ThingsService services = retrofit.create(ThingsService.class);
            listThingsService = services.getThingByLocation(location);


            List<ThingsModel> listThingsResponse = listThingsService.execute().body();

            return listThingsResponse;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
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
