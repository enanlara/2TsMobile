package br.com.a2ts_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.a2ts_mobile.Things_Manager.GetLocationsAsync;
import br.com.a2ts_mobile.Things_Manager.LocationModel;
import br.com.a2ts_mobile.User_Interface.EditThingsActivity;
import br.com.a2ts_mobile.User_Interface.ListThingsActivity;
import br.com.a2ts_mobile.User_Interface.LoginActivity;
import br.com.a2ts_mobile.User_Manager.UserModel;

public class MainActivity extends AppCompatActivity {
    private EditText edt_number_things;
    private TextView txt_num;
    private TextView txt_location;
    private Spinner spn_location;
    private Button btn_seach;
    private int tipoDeConsuta = 1;

    private List<String> listLocation = new ArrayList<>();
    private List<LocationModel> responseListLocation = new ArrayList<>();
    private ArrayAdapter<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(UserModel.ID == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        setTitle("Busca por localização");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spn_location = (Spinner) findViewById(R.id.spn_location);
        btn_seach = (Button) findViewById(R.id.btn_search);
        edt_number_things = (EditText) findViewById(R.id.edt_num);
        txt_num = (TextView) findViewById(R.id.txt_num);
        txt_location = (TextView) findViewById(R.id.txt_location);
        txt_num.setVisibility(View.GONE);
        edt_number_things.setVisibility(View.GONE);
        options = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        GetLocationsAsync sync = new GetLocationsAsync(this, new GetLocationsAsync.onResponseRetrofitListnner() {
            @Override
            public void responseLocations(List<LocationModel> response) {
                responseListLocation = response;
                response.add(0, new LocationModel(0, "Selecione um local"));
                LocationModel.listLocations = response;
                if(response == null){
                    Toast.makeText(MainActivity.this, "Não foi possivel conectar com o servidor. Verifique a conexão com a internet e tente novamente!!!", Toast.LENGTH_SHORT).show();
                    btn_seach.setEnabled(false);
                }else {
                    for (int i = 0; i < response.size(); i++) {
                        listLocation.add(response.get(i).getId().toString());
                        Log.i("99999999999 ", response.get(i).getRoom());
                        options.add(response.get(i).getRoom().toString());

                    }
                }
            }
        });
        sync.execute();

        spn_location.setAdapter(options);

        btn_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String locationSelected = spn_location.getSelectedItem().toString();
                String localizacao = listLocation.get(spn_location.getSelectedItemPosition());
                String dataSearch;
                if (tipoDeConsuta == 4){
                    dataSearch = edt_number_things.getText().toString();
                }else{
                    dataSearch = localizacao;
                }

                Intent listThingsAct = new Intent(MainActivity.this, ListThingsActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("typesearch",tipoDeConsuta);
                extras.putString("datasearch",dataSearch);
                listThingsAct.putExtras(extras);

                startActivity(listThingsAct);
            }
        });
//        startActivity(new Intent(this, ListThingsActivity.class));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(1, 1, 0, "Busca por localização");
        menu.add(1, 2, 1, "Busca coisas sobrando por localização");
        menu.add(1, 3, 2, "Busca coisas faltando por localização");
        menu.add(1, 4, 3, "Busca coisa por codigo");
        menu.add(1, 5, 4, "Sair");

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1){
            setTitle("Busca por localização");
            txt_num.setVisibility(View.GONE);
            edt_number_things.setVisibility(View.GONE);
            txt_location.setVisibility(View.VISIBLE);
            spn_location.setVisibility(View.VISIBLE);
        }else if(item.getItemId() == 2){
            setTitle("Busca coisas sobrando por localização");
            txt_num.setVisibility(View.GONE);
            edt_number_things.setVisibility(View.GONE);
            txt_location.setVisibility(View.VISIBLE);
            spn_location.setVisibility(View.VISIBLE);
        }else if(item.getItemId() == 3){
            setTitle("Busca coisas faltando por localização");
            txt_num.setVisibility(View.GONE);
            edt_number_things.setVisibility(View.GONE);
            txt_location.setVisibility(View.VISIBLE);
            spn_location.setVisibility(View.VISIBLE);
        }else if(item.getItemId() == 4){
            setTitle("Busca coisas por codigo");
            txt_location.setVisibility(View.GONE);
            spn_location.setVisibility(View.GONE);
            txt_num.setVisibility(View.VISIBLE);
            edt_number_things.setVisibility(View.VISIBLE);
        }else if(item.getItemId() == 5){
            UserModel.ID = null;
            UserModel.PERMISSION = null;
            UserModel.TOKEN = null;
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return false;
        }
        tipoDeConsuta = item.getItemId();
        return true;
    }
}
