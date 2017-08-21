package br.com.a2ts_mobile.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.Things_Manager.ThingsModel;

public class DetailsThingsActivity extends AppCompatActivity {

    private TextView numThings;
    private TextView description;
    private TextView situation;
    private TextView value;
    private TextView dateRegistre;
    private TextView state;
    private TextView note;
    private TextView location;
    private TextView locationCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_things);

        Intent intent = getIntent();
        ThingsModel thingsModel = (ThingsModel) intent.getSerializableExtra("things");

        numThings = (TextView) findViewById(R.id.numThings);
        description = (TextView) findViewById(R.id.description);
        situation = (TextView) findViewById(R.id.situation);
        value = (TextView) findViewById(R.id.value);
        dateRegistre = (TextView) findViewById(R.id.date_registre);
        state = (TextView) findViewById(R.id.state);
        note = (TextView) findViewById(R.id.note);
        location = (TextView) findViewById(R.id.location);
        locationCurrent = (TextView) findViewById(R.id.location_current);




        numThings.setText( thingsModel.getNrThings1().toString());
        description.setText( thingsModel.getDescription().toString());
        situation.setText( thingsModel.getSituation().toString());
        value.setText((thingsModel.getValue().equals(null))?"Valor indefinido":"R$"+String.valueOf(thingsModel.getValue()));
        dateRegistre.setText( thingsModel.getDateRegistre());
        state.setText( thingsModel.getState().toString());
        note.setText( thingsModel.getNote().toString());
        location.setText( thingsModel.getLocation().getRoom().toString());
        locationCurrent.setText( thingsModel.getLocationCurrent().getRoom().toString());


////        location.setText( thingsModel.getLocation().toString());


    }
}
