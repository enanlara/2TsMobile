package br.com.a2ts_mobile.User_Interface;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.Things_Manager.GetLocationsAsync;
import br.com.a2ts_mobile.Things_Manager.LocationModel;
import br.com.a2ts_mobile.User_Manager.UserModel;
import br.com.a2ts_mobile.Util.onResponseRetrofitListnnerLocations;

public class MainActivity extends AppCompatActivity {
    private final String textoAjudaBuscaPorSetor = "Busca itens(patrimônio) de acordo com o setor selecionado.\n" +
            "• Selecione o setor e clique no botão \"BUSCAR\" para buscar os itens.\n" +
            "• Após clicar no botão o sistema será redirecionado para uma tela onde será listado os itens do patrimônio que estão no setor da busca.\n" +
            "• 'SETOR' é o local físico onde estão os itens de patrimônio. ";
    private final String textoAjudaBuscaSobrandoPorSetor = "Busca itens(patrimônio) que pertencem a outros setores, mas se encontram no setor onde foi realizada a busca" +
            "• Selecione o setor e clique no botão \"BUSCAR\" para buscar os itens\n" +
            "• Após clicar no botão o sistema será redirecionado para uma tela onde será listado os itens que pertencem a outros patrimônios, mas estão no setor da busca.\n" +
            "• 'SETOR' é o local físico onde estão os itens de patrimônio ";
    private final String textoAjudaBuscaFaltandoPorSetor = "Busca itens(patrimônio) que pertencem ao setor que foi feito a busca, mas não se encontra no setor\n" +
            "• Selecione o setor e clique no botão \"BUSCAR\" para buscar os itens\n" +
            "• Após clicar no botão o sistema será redirecionado para uma tela onde será listado os itens que pertencem ao setor onde foi feita a busca mas não estão no local.\n" +
            "• 'SETOR' é o local físico onde estão os itens de patrimônio ";
    private final String textoAjudaBuscaPorNumero = "Busca item(patrimônio) pelo número da placa.\n" +
            "• Digite o número do item e clique no botão \"BUSCAR\" para buscar o item\n" +
            "• Após clicar no botão o sistema será redirecionado para uma tela onde será listado o item relacionado ao número.\n";
    private EditText edt_number_things;
    private TextView txt_num;
    private TextView txt_location;
    private Spinner spn_location;
    private Button btn_seach;
    private int tipoDeConsuta = 1;

    private Button btnPorSetor;
    private Button btnSPorSetor;
    private Button btnFPorSetor;
    private Button btnPorNum;

    private List<String> listLocation = new ArrayList<>();
    private List<LocationModel> responseListLocation = new ArrayList<>();
    private ArrayAdapter<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (UserModel.ID == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        setTitle("Selecione uma opção de busca");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPorSetor = (Button) findViewById(R.id.buttonMenuBuscaPorSetor);
        btnFPorSetor = (Button) findViewById(R.id.buttonMenuBuscaItensFaltandoPorSetor);
        btnSPorSetor = (Button) findViewById(R.id.buttonMenuBuscaItensSobrandoPorSetor);
        btnPorNum = (Button) findViewById(R.id.buttonMenuBuscaItensPorNumero);

        spn_location = (Spinner) findViewById(R.id.spn_location);
        btn_seach = (Button) findViewById(R.id.btn_search);
        edt_number_things = (EditText) findViewById(R.id.edt_num);
        txt_num = (TextView) findViewById(R.id.txt_num);
        txt_location = (TextView) findViewById(R.id.txt_location);
        txt_num.setVisibility(View.GONE);
        edt_number_things.setVisibility(View.GONE);
        options = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        txt_location.setVisibility(View.GONE);
        spn_location.setVisibility(View.GONE);
        btn_seach.setVisibility(View.GONE);

        if(LocationModel.listLocations.size() == 0 ) {
            GetLocationsAsync sync = new GetLocationsAsync(this, new onResponseRetrofitListnnerLocations() {
                @Override
                public void responseLocations(List<LocationModel> response) {
                    responseListLocation = response;
                    response.add(0, new LocationModel(0, "Selecione um setor"));
                    LocationModel.listLocations = response;
                    if (response == null) {
                        Toast.makeText(MainActivity.this, "Não foi possivel conectar com o servidor. Verifique a conexão com a internet e tente novamente!!!", Toast.LENGTH_SHORT).show();
                        btn_seach.setEnabled(false);
                    }else {
                        for (int i = 0; i < LocationModel.listLocations.size(); i++) {
                            listLocation.add(LocationModel.listLocations.get(i).getId().toString());
                            options.add(LocationModel.listLocations.get(i).getRoom().toString());

                        }
                    }
                }
            });
            sync.execute();
        }else {
            for (int i = 0; i < LocationModel.listLocations.size(); i++) {
                listLocation.add(LocationModel.listLocations.get(i).getId().toString());
                options.add(LocationModel.listLocations.get(i).getRoom().toString());

            }
        }
        spn_location.setAdapter(options);

        btn_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String locationSelected = spn_location.getSelectedItem().toString();
                String localizacao = listLocation.get(spn_location.getSelectedItemPosition());
                String dataSearch;
                if (tipoDeConsuta == 4) {
                    dataSearch = edt_number_things.getText().toString();
                } else {
                    dataSearch = localizacao;
                }
                Log.i("dddddddd",dataSearch);
                if(!dataSearch.trim().equals("") && !dataSearch.equals("0")) {
                    Intent listThingsAct = new Intent(MainActivity.this, ListThingsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("typesearch", tipoDeConsuta);
                    extras.putString("datasearch", dataSearch);
                    listThingsAct.putExtras(extras);

                    startActivity(listThingsAct);
                    finish();
                }
            }
        });
//        startActivity(new Intent(this, ListThingsActivity.class));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 0, "Busca itens por setor");
        menu.add(1, 2, 1, "Busca itens sobrando por setor");
        menu.add(1, 3, 2, "Busca itens faltando por setor");
        menu.add(1, 4, 3, "Busca item por número");
        menu.add(1, 5, 4, "Sair");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 1) {
            acaoMenuPorSetor();
        } else if (item.getItemId() == 2) {
            acaoMenuSobrandoPorSetor();
        } else if (item.getItemId() == 3) {
            acaoMenuFaltandoPorSetor();
        } else if (item.getItemId() == 4) {
            acaoMenuPorNumero();
        } else if (item.getItemId() == 5) {
            new AlertDialog.Builder(this)
                    .setTitle("Sair da aplicação")
                    .setMessage("Tem certeza que deseja sair da aplicação?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserModel.ID = null;
                            UserModel.PERMISSION = null;
                            UserModel.TOKEN = null;
                            SharedPreferences sp1 = getSharedPreferences(UserModel.PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp1.edit();
                            editor.putInt("userId", -1);
                            editor.putInt("userPermission", -1);
                            editor.putString("userToken", null);
                            editor.commit();
                            finish();
                            System.exit(0);
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();

            return false;
        }
        tipoDeConsuta = item.getItemId();
        return true;
    }

    private void AlertAjuda(String menssagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajuda");
        builder.setMessage(menssagem);
        builder.create();
        builder.show();
    }

    public void alertAjuda1(View view) {
        AlertAjuda(textoAjudaBuscaPorSetor);
    }

    public void alertAjuda2(View view) {
        AlertAjuda(textoAjudaBuscaSobrandoPorSetor);
    }
    public void alertAjuda3(View view) {
        AlertAjuda(textoAjudaBuscaFaltandoPorSetor);
    }
    public void alertAjuda4(View view) {
        AlertAjuda(textoAjudaBuscaPorNumero);
    }

    public void buscaPorSetor(View view) {
        acaoMenuPorSetor();
    }

    public void buscaSobrandoPorSetor(View view) {
        acaoMenuSobrandoPorSetor();
    }

    public void buscaFaltandoPorSetor(View view) {
        acaoMenuFaltandoPorSetor();
    }

    public void buscaPorNumero(View view) {
        acaoMenuPorNumero();
    }
    private void acaoMenuPorSetor(){
        setTitle("Busca itens por setor");
        txt_num.setVisibility(View.GONE);
        edt_number_things.setVisibility(View.GONE);
        txt_location.setVisibility(View.VISIBLE);
        spn_location.setVisibility(View.VISIBLE);
        btn_seach.setVisibility(View.VISIBLE);
        btnPorSetor.setEnabled(false);
        btnFPorSetor.setEnabled(true);
        btnSPorSetor.setEnabled(true);
        btnPorNum.setEnabled(true);
        tipoDeConsuta = 1;

    }
    private void acaoMenuSobrandoPorSetor(){
        setTitle("Busca itens sobrando por setor");
        txt_num.setVisibility(View.GONE);
        edt_number_things.setVisibility(View.GONE);
        txt_location.setVisibility(View.VISIBLE);
        spn_location.setVisibility(View.VISIBLE);
        btn_seach.setVisibility(View.VISIBLE);
        btnSPorSetor.setEnabled(false);
        btnPorNum.setEnabled(true);
        btnFPorSetor.setEnabled(true);
        btnPorSetor.setEnabled(true);
        tipoDeConsuta = 2;

    }
    private void acaoMenuFaltandoPorSetor(){
        setTitle("Busca itens faltando por setor");
        txt_num.setVisibility(View.GONE);
        edt_number_things.setVisibility(View.GONE);
        txt_location.setVisibility(View.VISIBLE);
        spn_location.setVisibility(View.VISIBLE);
        btn_seach.setVisibility(View.VISIBLE);
        btnFPorSetor.setEnabled(false);
        btnSPorSetor.setEnabled(true);
        btnPorNum.setEnabled(true);
        btnPorSetor.setEnabled(true);
        tipoDeConsuta = 3;


    }
    private void acaoMenuPorNumero(){
        setTitle("Busca itens por número");
        txt_location.setVisibility(View.GONE);
        spn_location.setVisibility(View.GONE);
        txt_num.setVisibility(View.VISIBLE);
        edt_number_things.setVisibility(View.VISIBLE);
        btn_seach.setVisibility(View.VISIBLE);
        btnPorNum.setEnabled(false);
        btnPorSetor.setEnabled(true);
        btnSPorSetor.setEnabled(true);
        btnFPorSetor.setEnabled(true);
        tipoDeConsuta = 4;

    }
}
