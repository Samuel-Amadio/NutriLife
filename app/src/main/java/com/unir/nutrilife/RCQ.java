package com.unir.nutrilife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RCQ extends AppCompatActivity {

    private EditText editTextCintura;
    private EditText editTextQuadril;
    private Button btnProsseguir;
    private Button voltar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rcq);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextCintura = findViewById(R.id.editTextCintura);
        editTextQuadril = findViewById(R.id.editTextQuadril);
        btnProsseguir = findViewById(R.id.btnProsseguir);
        voltar1 = findViewById(R.id.btnVoltar1);

        Intent intentRecebida = getIntent();
        String sexo = intentRecebida.getStringExtra("Sexo");
        String nome = intentRecebida.getStringExtra("Nome");
        String classificacao = intentRecebida.getStringExtra("Classificação");
        String riscos = intentRecebida.getStringExtra("Riscos");
        float imc = intentRecebida.getFloatExtra("IMC", 0);

        btnProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCintura.getText().toString().isEmpty() || editTextQuadril.getText().toString().isEmpty()){
                    Toast.makeText(RCQ.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    float cintura = Float.parseFloat(editTextCintura.getText().toString());
                    float quadril = Float.parseFloat(editTextQuadril.getText().toString());
                    float rcq = cintura / quadril;

                    Intent intent = getIntent();
                    String sexo = intent.getStringExtra("Sexo");
                    String classificacaoRCQ = "";
                    switch (sexo) {
                        case "Masculino":
                            if(rcq < 0.9){
                                classificacaoRCQ = "Normal";

                            }else{
                                classificacaoRCQ = "Risco aumentado";
                            }
                            break;
                        case "Feminino" :
                            if(rcq < 0.85){
                                classificacaoRCQ = "Normal";
                            }else{
                                classificacaoRCQ = "Risco aumentado";
                            }
                            break;

                    }

                    Intent intentFinal = new Intent(RCQ.this, Resultado.class);
                    intentFinal.putExtra("Nome", nome);
                    intentFinal.putExtra("Classificação", classificacao);
                    intentFinal.putExtra("IMC", imc);
                    intentFinal.putExtra("Riscos", riscos);
                    intentFinal.putExtra("ClassificacaoRCQ", classificacaoRCQ);
                    startActivity(intentFinal);
                }
            }
        });

        voltar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}