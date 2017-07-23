package br.com.a2ts_mobile.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.Things_Manager.ThingsModel;

public class DetailsThingsActivity extends AppCompatActivity {

    private TextView codeThings;
    private TextView description;
    private TextView state;
    private TextView location;
    private TextView situation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_things);

        Intent intent = getIntent();
        ThingsModel thingsModel = (ThingsModel) intent.getSerializableExtra("things");
//        Log.i("eeeee", thingsModel.getName());
        codeThings = (TextView) findViewById(R.id.codeThings);
        description = (TextView) findViewById(R.id.description);
        state = (TextView) findViewById(R.id.state);
        location = (TextView) findViewById(R.id.location);
        situation = (TextView) findViewById(R.id.situation);

        codeThings.setText( thingsModel.getCodeThing().toString());
        description.setText( thingsModel.getDescription().toString());
        state.setText( thingsModel.getState().toString());
        location.setText( thingsModel.getLocation().toString());
        situation.setText( thingsModel.getSituation().toString());


    }
}
