package br.com.a2ts_mobile.User_Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.a2ts_mobile.Synchronization_Manager.SynchronizationThingsService;
import br.com.a2ts_mobile.Synchronization_Manager.SynchronizeThings;
import br.com.a2ts_mobile.Things_Manager.ThingsModel;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Enan on 6/17/2017.
 */

public class UserAsync extends AsyncTask<Void, Void, UserModel> {
    private Context context;
    public ProgressDialog dialog;
    public UserAsync.onResponseRetrofitListnner listnner;
    private UserModel userModel;

    public UserAsync(Context context, UserAsync.onResponseRetrofitListnner listnner, UserModel userModel) {
        this.context = context;
        this.listnner = listnner;
        this.userModel = userModel;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Carregando dados", "Aguarde", true, true );
    }

    @Override
    protected UserModel doInBackground(Void... params) {
        return editThings();
    }
    private UserModel editThings(){
        try {

            Call<UserModel> listThingsService = null;

            String baseUrl = "https://dg-2ts-server.herokuapp.com";

            Gson gsonConverter = new GsonBuilder().registerTypeAdapter(UserModel.class, new UserDeserialization())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                    .build();

            final UserService services = retrofit.create(UserService.class);
            listThingsService = services.logar(this.userModel.getEmail(), this.userModel.getPassword());


            UserModel response = listThingsService.execute().body();
            if(response.getId() == null){
                return null;
            }else{
               return response;
            }
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }



    @Override
    protected void onPostExecute(UserModel response) {
        listnner.responseUser(response);
        dialog.dismiss();
    }

    public interface onResponseRetrofitListnner{
        public void responseUser(UserModel response);
    }
}
