package br.com.a2ts_mobile.User_Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Enan on 6/17/2017.
 */

public class UserAsync extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    public ProgressDialog dialog;
    public UserAsync.onResponseRetrofitListnner listnner;


    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Carregando dados", "Aguarde", true, true );
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return true;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        listnner.responseBens(response);
        dialog.dismiss();
    }

    public interface onResponseRetrofitListnner{
        public void responseBens(Boolean response);
    }
}
