package com.devlist.app.screens.task;

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
import com.devlist.app.data.models.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class EditTask extends AppCompatActivity {

    private EditText edtTituloTarefa, edtNotasTarefa, edtHoraInicio, edtHoraFim;
    private RadioGroup radioGroupPrioridade; // PRIORIDADE B/M/A
    private Button btnEditarTarefa, btnExcluirTarefa; // BOTOES EDITAR E EXCLUIR

    private FirebaseFirestore databaseReference;
    private String taskId; // ATRIBUTO PARA ARMAZENAR O ID DA TAREFA PARA SER EDITADA OU EXCLUIDA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // INTERLIGANDO AS DECLARACOES DAS ID'S DOS BOTOES/INPUT DO XML PARA O .JAVA
        edtTituloTarefa = findViewById(R.id.taskTitulo);
        edtNotasTarefa = findViewById(R.id.taskNotas);
        edtHoraInicio = findViewById(R.id.taskIniHora);
        edtHoraFim = findViewById(R.id.taskFimHora);
        radioGroupPrioridade = findViewById(R.id.taskPrioridade);
        btnEditarTarefa = findViewById(R.id.btnEditarTarefa);
        btnExcluirTarefa = findViewById(R.id.btnExcluirTarefa);

        // DATABASE FIREBASE
        databaseReference = FirebaseFirestore.getInstance();

        // CONFIGURANDO EVENTO DE CLIQUE PARA EDITAR A TAREFA
        btnEditarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarTarefa();
            }
        });

        // CONFIGURANDO EVENTO DE CLIQUE PARA EXCLUIR A TAREFA
        btnExcluirTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirTarefa();
            }
        });

        // CONFIGURAR PARA RECEBER O ID DA TAREFA A SER EDITADA OU EXLUIDA
        taskId = "ID_DA_TAREFA";
    }


    // EDICAO DA TAREFA
    private void editarTarefa() {

        // OBTENDO INFORMACOES DA INTERFACE (TELA) DOS DADOS INFORMADOS PELO USUARIO
        String titulo = edtTituloTarefa.getText().toString().trim();
        String notas = edtNotasTarefa.getText().toString().trim();
        String horaInicio = edtHoraInicio.getText().toString().trim();
        String horaFim = edtHoraFim.getText().toString().trim();

        // VERIFICA SE TODOS OS CAMPOS CONTINUAM PREENCHIDOS
        if (titulo.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        // OBTEM A PRIORIDADE ESCOLHIDA
        int idPrioridadeSelecionada = radioGroupPrioridade.getCheckedRadioButtonId();
        RadioButton prioridadeSelecionada = findViewById(idPrioridadeSelecionada);

        // CHAMANDO INSTANCIA DA CLASSE TAREFAS
        Task tarefaAtualizada = new Task(
                titulo,
                notas,
                new Date(),
                new Date(),
                getCodigoPrioridade(prioridadeSelecionada.getText().toString())
        );

        // EDICAO DA TAREFA
        // ATRAVES DO ID DA TAREFA A MESMA SERA EDITADA E APOS ENVIADA AO BANCO DE DADOS
        databaseReference.collection("tasks").document(taskId).set(tarefaAtualizada)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditTask.this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditTask.this, "Erro ao atualizar a tarefa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "Erro ao atualizar a tarefa"+ e.getMessage());
                    }
                });
    }

    // EXCLUSAO DA TAREFA
    private void excluirTarefa() {

        // EXCLUIR A TAREFA NO BANCO DE DADOS ATRAVES DA ID
        databaseReference.collection("tasks").document(taskId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditTask.this, "Tarefa excluída com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditTask.this, "Erro ao excluir a tarefa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "Erro ao excluir a tarefa"+ e.getMessage());
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
