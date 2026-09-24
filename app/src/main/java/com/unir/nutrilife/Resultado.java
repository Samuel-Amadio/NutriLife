package com.unir.nutrilife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Resultado extends AppCompatActivity {

    private Button btnVoltar;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtResultado = findViewById(R.id.txtResultado);
        btnVoltar = findViewById(R.id.btnVoltar);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("Nome");
        String riscos = intent.getStringExtra("Riscos");
        String classificacao = intent.getStringExtra("Classificação");
        String classificacaoRCQ = intent.getStringExtra("ClassificacaoRCQ");
        float imc = intent.getFloatExtra("IMC", 0);

        StringBuilder sb = new StringBuilder();

        sb.append("Olá, ").append(nome).append(".\n");
        sb.append("Seu IMC é: ").append(String.format("%.2f", imc)).append("\n");
        sb.append("Classificação: ").append(classificacao).append(".\n");
        sb.append("\nAbaixo estão os riscos associados ao seu resultado: \n").append(riscos);

        if(!classificacaoRCQ.isEmpty()){
            sb.append("\n\nSua classificação de RCQ de acordo com o seu sexo é: ").append(classificacaoRCQ);
        }



        txtResultado.setText(sb.toString());

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}