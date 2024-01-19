package com.devlist.app.screens.task;

import android.content.Intent;
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
import com.devlist.app.R;
import com.devlist.app.screens.dashboard.Dashboard;
import com.devlist.app.data.models.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CreateTask extends AppCompatActivity {

    private EditText edtTituloTarefa, edtNotasTarefa, edtHoraInicio, edtHoraFim;
    private RadioGroup radioGroupPrioridade; // PRIORIDADE B/M/A
    private Button btnAdicionarTarefa, btnBackAddTask; // BOTAO ADICIONA TAREFA
    private FirebaseFirestore databaseReference;
    private FirebaseAuth myAuth;

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
        btnBackAddTask = findViewById(R.id.btnBackAddTask);

        // DATABASE FIREBASE
        databaseReference = FirebaseFirestore.getInstance();

        // EVENTO DE CLIQUE PARA ADICIONAR A TAREFA
        btnAdicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarTarefa();
            }
        });
        btnBackAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
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
        Log.d("DEBUG", " Notas: " + notas);
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

        // PEGANDO O UID DO USUÁRIO
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user  = myAuth.getCurrentUser();
        String userId = user.getUid();

        // CHAMANDO INSTANCIA DA CLASSE TAREFAS
        Task tarefa = new Task(
                titulo,
                notas,
                new Date(),
                new Date(),
                getCodigoPrioridade(prioridadeSelecionada.getText().toString()),
                userId
        );

        // AO CLICAR, ENVIA OS DADOS PARA PODER SALVAR NO BANCO
        databaseReference.collection("tasks").add(tarefa)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateTask.this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show(); // ALERT
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish(); // FECHA A ATIVIDADE APOS ADICIONAR A TAREFA
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateTask.this, "Erro ao adicionar a tarefa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "Erro ao adicionar a tarefa"+ e.getMessage()); // LOG ERRO
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
