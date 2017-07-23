package br.com.a2ts_mobile.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.Synchronization_Manager.SynchronizeThings;
import br.com.a2ts_mobile.Things_Manager.ThingsModel;

public class EditThingsActivity extends AppCompatActivity {

    private ThingsModel thingsModel;
    private EditText codeThings;
    private EditText description;
    private EditText state;
    private EditText location;
    private EditText situation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_things);
//
        Intent intent = getIntent();
        this.thingsModel = (ThingsModel) intent.getSerializableExtra("things");

        codeThings = (EditText) findViewById(R.id.codeThings);
        description = (EditText) findViewById(R.id.description);
        state = (EditText) findViewById(R.id.state);
        location = (EditText) findViewById(R.id.location);
        situation = (EditText) findViewById(R.id.situation);

       // Toast.makeText(EditThingsActivity.this, thingsModel.getName(), Toast.LENGTH_SHORT).show();

        codeThings.setText(this.thingsModel.getCodeThing().toString());
        description.setText(this.thingsModel.getDescription());
        state.setText(this.thingsModel.getState());
        location.setText(this.thingsModel.getLocation());
        situation.setText(thingsModel.getSituation());
        getThingsModel();
        ThingsModel thingsModel = this.thingsModel;

//

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
                getThingsModel();
                ThingsModel thingsModel = this.thingsModel;

                final SynchronizeThings sync = new SynchronizeThings(EditThingsActivity.this, new SynchronizeThings.onResponseRetrofitListnner() {
                    @Override
                    public void responseEditThing(String response) {
                        if(response == null){
                            Toast.makeText(EditThingsActivity.this, "Não foi possivel alterar o objeto.Verifique a conexão com a internet!", Toast.LENGTH_SHORT).show();
                        }else{
                        if(response.equals("OK")){
                            Toast.makeText(EditThingsActivity.this, "Objeto alterado com sucesso!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditThingsActivity.this, "Erro ao alterar objeto!", Toast.LENGTH_SHORT).show();
                        }
                        }
                    }

//                    @Override
//                    public void responseThings(List<ThingsModel> response) {
//                        listThingsModels = response;
//                        if(listThingsModels != null) {
////                    Log.i("ddddd", String.valueOf(response.size()));
////                    List<ThingsModel> listThingsModel = new ArrayList<ThingsModel>();
//                            ArrayAdapter<ThingsModel> adapter = new ArrayAdapter<ThingsModel>(ListThingsActivity.this, android.R.layout.simple_list_item_1, response);
//                            listWiewThings.setAdapter(adapter);
//                        }
//
//                    }
                }, this.thingsModel);

                sync.execute();

//                SynchronizeThings synchronizeThings = new SynchronizeThings(this);

//                if(thingsModel.getId() != null){
////                    synchronizeThings.update(thingsModel);
//                }


//                synchronizeThings.close();
              //  Toast.makeText(EditThingsActivity.this, "Objeto " + thingsModel.getName() + " Alterado!", Toast.LENGTH_SHORT).show();
               // finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getThingsModel() {
        this.thingsModel.setCodeThing(codeThings.getText().toString());
        this.thingsModel.setDescription(description.getText().toString());
        this.thingsModel.setState(state.getText().toString());
        this.thingsModel.setLocation(location.getText().toString());
        this.thingsModel.setSituation(situation.getText().toString());
    }

}
