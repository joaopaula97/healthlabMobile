package br.unibh.sdm.healthlabexames.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.unibh.sdm.healthlabexames.R;
import br.unibh.sdm.healthlabexames.api.ExamesService;
import br.unibh.sdm.healthlabexames.api.RestServiceGenerator;
import br.unibh.sdm.healthlabexames.entidades.Exame;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String[] tipoExames ={"Admissional","Periódico","Mudança de função","Retorno ao trabalho","Demissional","Avaliação ocupacional","Avaliação de enquadramento de PCD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Cadastro de Exame");
        configuraBotaoSalvar();
        criaAcaoBotaoFlutuante();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,tipoExames);
        AutoCompleteTextView actv =  (AutoCompleteTextView)findViewById(R.id.idExame);
        actv.setThreshold(1);
        actv.setAdapter(adapter);
        actv.setTextColor(Color.RED);
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.idbotaoenviar);
        botaoSalvar.setOnClickListener(v -> {
            Log.i("FormularioExame","Clicou em Enviar");
            Exame exame = recuperaInformacoesFormulario();
            if (exame != null){
                salvaExame(exame);
            }
        });
    }

    private void criaAcaoBotaoFlutuante() {
        FloatingActionButton botaoNovo = findViewById(R.id.floatingActionButtonPesquisar);
        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","Clicou no botão para ir a página de pesquisa");
                startActivity(new Intent(MainActivity.this,
                        pesquisarExames.class));

            }
        });
    }

    private void salvaExame(Exame exame) {
        ExamesService service = RestServiceGenerator.createService(ExamesService.class);
        Call<Exame> call = service.atualizaexame(exame);
        call.enqueue(new Callback<Exame>() {
            @Override
            public void onResponse(Call<Exame> call, Response<Exame> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioExame", "Salvou o Exame "+ exame.getMatricula());
                    Toast.makeText(getApplicationContext(), "Salvou o Exame "+ exame.getMatricula(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioExame", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores - "+response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Exame> call, Throwable t) {
                Log.e("FormularioExame", "Erro: " + t.getMessage());
            }
        });
    }

    @NotNull
    private Exame recuperaInformacoesFormulario() {
        EditText matricula = findViewById(R.id.idMatricula);
        EditText nomeexame = findViewById(R.id.idExame);
        EditText data = findViewById(R.id.idData);
        Log.i("FormularioExame", "Data = "+data.getText().toString());
        Exame exame = new Exame();
        exame.setMatricula(matricula.getText().toString());
        exame.setNomeExame(nomeexame.getText().toString());
        try {
            exame.setDataExame(df.parse(data.getText().toString()));
            return exame;
        } catch (ParseException e) {
            Log.w("FormularioExame", "Data = "+data.getText().toString()+" nao pode ser formatada.");
            Toast.makeText(getApplicationContext(), "A data informada está incorreta. Favor conferir", Toast.LENGTH_LONG).show();
            return null;
        }
    }
}