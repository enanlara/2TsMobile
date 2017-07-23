package br.com.a2ts_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.a2ts_mobile.Things_Manager.GetLocationsAsync;
import br.com.a2ts_mobile.Things_Manager.Location;
import br.com.a2ts_mobile.User_Interface.EditThingsActivity;
import br.com.a2ts_mobile.User_Interface.ListThingsActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner spn_location;
    private Button btn_seach;

    private List<String> listLocation = new ArrayList<>();
    private List<Location> responseListLocation = new ArrayList<>();
    private ArrayAdapter<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spn_location = (Spinner) findViewById(R.id.spn_location);
        btn_seach = (Button) findViewById(R.id.btn_search);
        options = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        GetLocationsAsync sync = new GetLocationsAsync(this, new GetLocationsAsync.onResponseRetrofitListnner() {
            @Override
            public void responseLocations(List<Location> response) {
                responseListLocation = response;
                if(response == null){
                    Toast.makeText(MainActivity.this, "Não foi possivel conectar com o servidor. Verifique a conexão com a internet e tente novamente!!!", Toast.LENGTH_SHORT).show();
                    btn_seach.setEnabled(false);
                }else {
                    for (int i = 0; i < response.size(); i++) {
                        listLocation.add(response.get(i).getLocation());
                        Log.i("99999999999 ", response.get(i).getLocation());
                        options.add(response.get(i).getLocation().toString());

                    }
                }
            }
        });
        sync.execute();

        spn_location.setAdapter(options);

        btn_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationSelected = spn_location.getSelectedItem().toString();
//                Location localizacao = responselocList.get(sp.getSelectedItemPosition());
                Intent listThingsAct = new Intent(MainActivity.this, ListThingsActivity.class);
                listThingsAct.putExtra("location", locationSelected);
                startActivity(listThingsAct);
            }
        });
//        startActivity(new Intent(this, ListThingsActivity.class));
    }

}
