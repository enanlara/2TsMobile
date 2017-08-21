package br.com.a2ts_mobile.User_Interface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.a2ts_mobile.MainActivity;
import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.Things_Manager.ThingsModel;
import br.com.a2ts_mobile.User_Manager.UserAsync;
import br.com.a2ts_mobile.User_Manager.UserModel;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (EditText)findViewById(R.id.editTextLogin);
        password = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel usuario = new UserModel();
                usuario.setEmail(login.getText().toString().trim());
                usuario.setPassword(password.getText().toString().trim());
                final UserAsync sync = new UserAsync(LoginActivity.this, new UserAsync.onResponseRetrofitListnner() {
                    @Override
                    public void responseUser(UserModel response) {
                        if (response == null) {
                            Toast.makeText(LoginActivity.this, "Nenhum objeto encontrado!", Toast.LENGTH_SHORT).show();
                        } else {
                            UserModel.ID = response.getId();
                            UserModel.PERMISSION = response.getPermission();
                            UserModel.TOKEN = response.getToken();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                    }
//                    @Override
//                    public void responseThings(List<ThingsModel> response) {
//                        if(response == null){
//                            Toast.makeText(LoginActivity.this, "Nenhum objeto encontrado!", Toast.LENGTH_SHORT).show();
//                        }else {
//                            listThingsModels = response;
//                            if (listThingsModels != null) {
////                    Log.i("ddddd", String.valueOf(response.size()));
////                    List<ThingsModel> listThingsModel = new ArrayList<ThingsModel>();
//                                ArrayAdapter<ThingsModel> adapter = new ArrayAdapter<ThingsModel>(ListThingsActivity.this, android.R.layout.simple_list_item_1, response);
//                                listWiewThings.setAdapter(adapter);
//                            }
//                        }
//
//                    }
                }, usuario);
                sync.execute();

            }
        });
    }
}

