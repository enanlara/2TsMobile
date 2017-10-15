package br.com.a2ts_mobile.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.Synchronization_Manager.SynchronizeThings;
import br.com.a2ts_mobile.Things_Manager.LocationModel;
import br.com.a2ts_mobile.Things_Manager.ThingsModel;
import br.com.a2ts_mobile.Util.onResponseRetrofitListnnerSynchonize;

public class EditThingsActivity extends AppCompatActivity {

    private ThingsModel thingsModel;
    private TextView numThings;
    private TextView description;
    private EditText situation;
    private TextView value;
    private TextView dateRegistre;
    private EditText state;
    private TextView note;
    private TextView location;
    private Spinner locationCurrent;
    private Button btnSave;

    private ArrayAdapter<LocationModel> options ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_things);

        Intent intent = getIntent();
        this.thingsModel = (ThingsModel) intent.getSerializableExtra("things");

        numThings = (TextView) findViewById(R.id.numThings);
        description = (TextView) findViewById(R.id.description);
        situation = (EditText) findViewById(R.id.situation);
        value = (TextView) findViewById(R.id.value);
        dateRegistre = (TextView) findViewById(R.id.date_registre);
        state = (EditText) findViewById(R.id.state);
        note = (TextView) findViewById(R.id.note);
        location = (TextView) findViewById(R.id.location);
        locationCurrent = (Spinner) findViewById(R.id.spn_location_current);
        btnSave = (Button) findViewById(R.id.btn_save);

        options = new ArrayAdapter<LocationModel>(this, android.R.layout.simple_spinner_dropdown_item, LocationModel.listLocations);
        locationCurrent.setAdapter(options);


        // Toast.makeText(EditThingsActivity.this, thingsModel.getName(), Toast.LENGTH_SHORT).show();
        numThings.setText( thingsModel.getNrThings1().toString());
        numThings.setEnabled(false);
        description.setText( thingsModel.getDescription().toString());
        description.setEnabled(false);
        situation.setText( thingsModel.getSituation().toString());
        value.setText((thingsModel.getValue().equals(null))?"Valor indefinido":"R$"+String.valueOf(thingsModel.getValue()));
        value.setEnabled(false);
        dateRegistre.setText( thingsModel.getDateRegistre());
        dateRegistre.setEnabled(false);
        state.setText( thingsModel.getState().toString());
        note.setText( thingsModel.getNote().toString());
        location.setText( thingsModel.getLocation().getRoom().toString());
        location.setEnabled(false);
        Integer idLoc = thingsModel.getLocationCurrent().getId();

        if(idLoc != 0){
            int cont = 0;
            for (LocationModel locationModel: LocationModel.listLocations){
                if(locationModel.getId().equals(idLoc)){
                    locationCurrent.setSelection(cont);
                    break;
                }
                cont++;
            }
        }
//        numThings.setText(this.thingsModel.getNrThings1().toString());
//        description.setText(this.thingsModel.getDescription());
//        state.setText(this.thingsModel.getState());
////        location.setText(this.thingsModel.getLocation().getRoom());
//        situation.setText(thingsModel.getSituation());
//        getThingsModel();
//        ThingsModel thingsModel = this.thingsModel;

//
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acaoSalvar();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                acaoSalvar();

                break;

            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                this.finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getThingsModel() {
        this.thingsModel.setSituation(situation.getText().toString());
        this.thingsModel.setState(state.getText().toString());
        this.thingsModel.setNote(note.getText().toString());
        LocationModel locationCurrentSelected = (LocationModel)locationCurrent.getSelectedItem();
        this.thingsModel.setLocationCurrent(locationCurrentSelected);
       //        this.thingsModel.setLocation(new LocationModel());
    }
    private void acaoSalvar(){
        getThingsModel();

        final SynchronizeThings sync = new SynchronizeThings(EditThingsActivity.this, new onResponseRetrofitListnnerSynchonize() {
            @Override
            public void responseEditThing(String response) {
                if(response == null){
                    Toast.makeText(EditThingsActivity.this, "Não foi possivel alterar o objeto.Verifique a conexão com a internet!", Toast.LENGTH_SHORT).show();
                }else{
                    if(response.equals("OK")){
                        Toast.makeText(EditThingsActivity.this, "Objeto alterado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditThingsActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }, this.thingsModel);

        sync.execute();
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        this.finish(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
