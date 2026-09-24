package com.unir.nutrilife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextPeso;
    private EditText editTextAltura;
    private Button button;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextAltura = findViewById(R.id.editTextAltura);
        editTextNome = findViewById(R.id.editTextNome);
        editTextPeso = findViewById(R.id.editTextPeso);
        button = findViewById(R.id.btnCalcular);
        radioGroup = findViewById(R.id.radioGroup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextAltura.getText().toString().isEmpty() || editTextNome.getText().toString().isEmpty() || editTextPeso.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                } else if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Selecione um sexo", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    float altura = Float.parseFloat(editTextAltura.getText().toString());
                    float peso = Float.parseFloat(editTextPeso.getText().toString());
                    String nome = editTextNome.getText().toString();
                    int idSelecionado = radioGroup.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(idSelecionado);
                    String sexo = rb.getText().toString();

                    float imc = peso / (altura * altura);
                    String classificacao;
                    String riscos;
                    if(imc < 17 ){
                        classificacao = "Muito abaixo do peso";
                        riscos = "Maior risco de problemas de saúde, deficiências nutricionais,\n" +
                                "redução do desempenho físico e fraqueza/letargia";
                    }else if(imc > 17 && imc <=18.4 ){
                        classificacao = "Abaixo do peso";
                        riscos = "Maior risco de problemas relacionados ao baixo peso e deficiências\n" +
                                "nutricionais";
                    }else if( imc > 18.4 && imc <=24.9){
                        classificacao = "Peso normal";
                        riscos = "Faixa de peso considerada adequada para a maioria dos adultos";
                    }else if(imc > 24.9 && imc <=29.9){
                        classificacao = "Sobrepeso";
                        riscos = "Maior risco de alterações metabólicas, diabetes tipo 2 e doenças\n" +
                                "cardiovasculares";
                    }else if(imc > 29.9 && imc <=34.9){
                        classificacao = "Obesidade Grau I";
                        riscos = "Risco elevado de diabetes tipo 2, hipertensão e doenças\n" +
                                "cardiovasculares";
                    }else if(imc > 34.9 && imc <=39.9){
                    classificacao = "Obesidade Grau II";
                    riscos = "Risco muito elevado de complicações metabólicas, cardiovasculares\n" +
                            "e respiratórias";
                }else {
                    classificacao = "Obesidade Grau III";
                    riscos = "Risco muitíssimo elevado de comorbidades e comprometimento da\n" +
                            "saúde e qualidade de vida";
                };


                    AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);



                    janela.setTitle("Calcular IMC");
                    janela.setMessage("Deseja calcular o imc?");
                    janela.setPositiveButton("sim", new DialogInterface.OnClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            AlertDialog.Builder janelaRCQ = new AlertDialog.Builder((MainActivity.this));
                            janelaRCQ.setTitle("Cálculo opcional");
                            janelaRCQ.setMessage("Deseja calcular a RCQ (Relação Cintura-Quadril)?");
                            janelaRCQ.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, RCQ.class);
                                    intent.putExtra("Sexo", sexo);
                                    intent.putExtra("Nome", nome);
                                    intent.putExtra("Classificação", classificacao);
                                    intent.putExtra("IMC", imc);
                                    intent.putExtra("Riscos", riscos);
                                    startActivity(intent);
                                }
                            });

                            janelaRCQ.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, Resultado.class);
                                    intent.putExtra("Nome", nome);
                                    intent.putExtra("Classificação", classificacao);
                                    intent.putExtra("IMC", imc);
                                    intent.putExtra("Riscos", riscos);
                                    intent.putExtra("ClassificacaoRCQ", "");
                                    startActivity(intent);
                                }
                            });
                            janelaRCQ.show();
                        }
                    });

                    janela.setNegativeButton("Não", null);
                    janela.show();

                }
            }
        });




    }
}