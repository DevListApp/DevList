package com.devlist.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;

public class activity_criar_tarefa extends AppCompatActivity {

    private EditText edtTituloTarefa, edtNotasTarefa, edtHoraInicio, edtHoraFim;
    private RadioGroup radioGroupPrioridade; // PRIORIDADE B/M/A
    private Button btnAdicionarTarefa; // BOTAO ADICIONA TAREFA

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_tarefa);

        // INTERLIGANDO AS DECLARACOES DAS ID'S DOS BOTOES/INPUT DO XML PARA O .JAVA
        edtTituloTarefa = findViewById(R.id.taskTitulo);
        edtNotasTarefa = findViewById(R.id.taskNotas);
        edtHoraInicio = findViewById(R.id.taskIniHora);
        edtHoraFim = findViewById(R.id.taskFimHora);
        radioGroupPrioridade = findViewById(R.id.taskPrioridade);
        btnAdicionarTarefa = findViewById(R.id.btnAddTarefa);

        // DATABASE FIREBASE
        databaseReference = FirebaseDatabase.getInstance().getReference("tarefas");

        // EVENTO DE CLIQUE PARA ADICIONAR A TAREFA
        btnAdicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarTarefa();
            }
        });
    }

    private void adicionarTarefa() {
        // OBTENDO INFORMACOES DA INTERFACE (TELA) DOS DADOS INFORMADOS PELO USUARIO
        String titulo = edtTituloTarefa.getText().toString().trim();
        String notas = edtNotasTarefa.getText().toString().trim();
        String horaInicio = edtHoraInicio.getText().toString().trim();
        String horaFim = edtHoraFim.getText().toString().trim();

        // LOG PARA TESTAR OS DADOS ENVIADOS
        Log.d("DEBUG", "Titulo: " + titulo);
        Log.d("DEBUG", "Notas: " + notas);
        Log.d("DEBUG", "Hora Inicio: " + horaInicio);
        Log.d("DEBUG", "Hora Fim: " + horaFim);

        // VERIFICA SE TODOS OS CAMPOS ESTÃO PREENCHIDOS
        if (titulo.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        // OBTEM A PRIORIDADE ESCOLHIDA
        int idPrioridadeSelecionada = radioGroupPrioridade.getCheckedRadioButtonId();
        RadioButton prioridadeSelecionada = findViewById(idPrioridadeSelecionada);

        // LOG TESTAR
        Log.d("DEBUG", "Prioridade Selecionada: " + prioridadeSelecionada.getText().toString());


        // CHAMANDO INSTANCIA DA CLASSE TAREFAS
        Tarefas tarefa = new Tarefas(
                titulo,
                notas,
                new Date(),
                new Date(),
                getCodigoPrioridade(prioridadeSelecionada.getText().toString())
        );

        // AO CLICAR, ENVIA OS DADOS PARA PODER SALVAR NO BANCO
        databaseReference.push().setValue(tarefa)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity_criar_tarefa.this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show(); // ALERT
                            finish(); // FECHA A ATIVIDADE APOS ADICIONAR A TAREFA
                        } else {
                            Toast.makeText(activity_criar_tarefa.this, "Erro ao adicionar a tarefa: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "Erro ao adicionar a tarefa", task.getException()); // LOG ERRO
                        }
                    }
                });

    }

    private int getCodigoPrioridade(String prioridade) {
        // RETORNA QUAL É A PRIORIDADE ESCOLHIDA
        if ("Alta".equals(prioridade)) return 1;
        if ("Média".equals(prioridade)) return 2;
        if ("Baixa".equals(prioridade)) return 3;
        return 0;
    }
}
