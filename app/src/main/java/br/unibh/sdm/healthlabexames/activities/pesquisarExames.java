package br.unibh.sdm.healthlabexames.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.unibh.sdm.healthlabexames.R;
import br.unibh.sdm.healthlabexames.api.ExamesService;
import br.unibh.sdm.healthlabexames.api.RestServiceGenerator;
import br.unibh.sdm.healthlabexames.entidades.Exame;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pesquisarExames extends AppCompatActivity {

    private ExamesService service = null;
    final private pesquisarExames pesquisarexames = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_exames);
        service = RestServiceGenerator.createService(ExamesService.class);
        criaAcaoBotaoFlutuante();

    }

    private void criaAcaoBotaoFlutuante() {
        FloatingActionButton botaoNovo2 = findViewById(R.id.floatingActionButtonVoltar);
        botaoNovo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","Clicou no botão para voltar a tela de cadastro");
                startActivity(new Intent(pesquisarExames.this,
                        MainActivity.class));

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        buscarExames();
    }

    private void buscarExames(){
        ExamesService service = RestServiceGenerator.createService(ExamesService.class);
        Call<List<Exame>> call = service.getExames();
        call.enqueue(new Callback<List<Exame>>() {
            @Override
            public void onResponse(Call<List<Exame>> call, Response<List<Exame>> response) {
                if (response.isSuccessful()) {
                    Log.i("PesquisaActivity", "Retornou " + response.body().size() + " Exames!");
                    List<String> lista = new ArrayList<String>();
                    for (Exame item : response.body()) {
                        lista.add(item.getMatricula());

                    }
                    Log.i("PesquisaActivity", lista.toArray().toString());
                    ListView listView = findViewById(R.id.listaDeExames);
                    listView.setAdapter(new ArrayAdapter<String>(pesquisarexames,
                            android.R.layout.simple_list_item_1,
                            lista));
            }else {
                    Log.e("PesquisaActivity", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code() + "): " + response.message(), Toast.LENGTH_LONG).show();
                }

        }
            @Override
            public void onFailure(Call<List<Exame>> call, Throwable t) {
                Log.e("PesquisaActivity", "Erro: " + t.getMessage());
            }
        });



}





}

