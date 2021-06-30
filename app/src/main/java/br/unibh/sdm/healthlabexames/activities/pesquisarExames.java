package br.unibh.sdm.healthlabexames.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
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
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private final Context context;

    public pesquisarExames() {
        context = this;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_exames);
        service = RestServiceGenerator.createService(ExamesService.class);
        criaAcaoBotaoFlutuante();
        criaAcaoCliqueLongo();

    }
    private void criaAcaoCliqueLongo() {
        ListView listView = findViewById(R.id.listaDeExames1);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("PesquisaActivity","Clicou em clique longo na posicao "+position);
                final Exame objetoSelecionado = (Exame) parent.getAdapter().getItem(position);
                Log.i("PesquisaActivity", "Selecionou o Exame "+objetoSelecionado.getMatricula());
                new AlertDialog.Builder(parent.getContext()).setTitle("Removendo Exame")
                        .setMessage("Tem certeza que quer remover o exame "+objetoSelecionado.getMatricula()+"?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeExame(objetoSelecionado);
                            }
                        }).setNegativeButton("Não", null).show();
                return true;
            }
        });
    }

    private void removeExame(Exame exame) {
        Call<Boolean> call = null;
        Log.i("PesquisaActivity","Vai remover exame "+exame.getMatricula());
        call = this.service.excluiExame(exame.getMatricula());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Log.i("PesquisaActivity", "Removeu o exame " + exame.getMatricula());
                    Toast.makeText(getApplicationContext(), "Removeu o exame " + exame.getMatricula(), Toast.LENGTH_LONG).show();
                    onResume();
                } else {
                    Log.e("PesquisaActivity", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("PesquisaActivity", "Erro: " + t.getMessage());
            }
        });
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
                    List<String> lista2 = new ArrayList<String>();
                    List<String> lista3 = new ArrayList<String>();
                    for (Exame item : response.body()) {
                        lista2.add(item.getNomeExame());
                        lista3.add(df.format(item.getDataExame()));

                    }

                    ListView listView2 = findViewById(R.id.listaDeExames2);
                    listView2.setAdapter(new ArrayAdapter<String>(pesquisarexames,
                            android.R.layout.simple_list_item_1,
                            lista2));
                    ListView listView3 = findViewById(R.id.listaDeExames3);
                    listView3.setAdapter(new ArrayAdapter<String>(pesquisarexames,
                            android.R.layout.simple_list_item_1,
                            lista3));
                    ListView listView = findViewById(R.id.listaDeExames1);
                    listView.setAdapter(new ListaExamesAdapter(context,response.body()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("ListaCriptoActivity", "Selecionou o objeto de posicao "+position);
                            Exame objetoSelecionado = (Exame) parent.getAdapter().getItem(position);
                            Log.i("ListaCriptoActivity", "Selecionou o exame "+objetoSelecionado.getMatricula());

                        }
                    });
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

