package br.com.a2ts_mobile.User_Interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.User_Manager.UserAsync;
import br.com.a2ts_mobile.User_Manager.UserModel;
import br.com.a2ts_mobile.Util.onResponseRetrofitListnnerUsers;

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
        SharedPreferences sp1 = getSharedPreferences(UserModel.PREF_NAME, MODE_PRIVATE);
        int id = sp1.getInt("userId",-1);
        int permission = sp1.getInt("userPermission",-1);
        String token = sp1.getString("userToken", null);
        if(id != -1 && permission != -1 && token != null ){
            UserModel.ID = id;
            UserModel.PERMISSION = permission;
            UserModel.TOKEN = token;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }
        Log.i("teste", String.valueOf(sp1.getString("userToken","d")));
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
                if(!usuario.getEmail().trim().equals("") && !usuario.getPassword().trim().equals("")) {
                    final UserAsync sync = new UserAsync(LoginActivity.this, new onResponseRetrofitListnnerUsers() {
                        @Override
                        public void responseUser(UserModel response) {
                            if (response == null) {
                                Toast.makeText(LoginActivity.this, "Erro de autenticação!", Toast.LENGTH_SHORT).show();
                            } else {
                                UserModel.ID = response.getId();
                                UserModel.PERMISSION = response.getPermission();
                                UserModel.TOKEN = response.getToken();

                                SharedPreferences sp1 = getSharedPreferences(UserModel.PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp1.edit();
                                editor.putInt("userId", response.getId());
                                editor.putInt("userPermission", response.getPermission());
                                editor.putString("userToken", response.getToken());
                                editor.commit();

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                LoginActivity.this.finish();

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

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                this.finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}

