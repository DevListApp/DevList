package com.devlist.app.screens.task;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.devlist.app.R;
import com.devlist.app.data.repositories.TaskRepository;
import com.devlist.app.data.sources.TaskFirebaseDataSource;
import com.devlist.app.data.sources.UserFirebaseDataSource;
import com.devlist.app.screens.dashboard.Dashboard;
import com.devlist.app.data.models.Task;
import com.devlist.app.screens.user.RegisterUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateTask extends AppCompatActivity {

    private EditText edtTituloTarefa, edtNotasTarefa, edtHoraInicio, edtHoraFim;
    private RadioGroup radioGroupPrioridade; // PRIORIDADE B/M/A
    private Button btnAdicionarTarefa, btnBackAddTask; // BOTAO ADICIONA TAREFA
    private FirebaseFirestore databaseReference;
    private FirebaseAuth myAuth;
    private DatePickerDialog picker;
    private Date dataInicial;
    private Date dataFinal;
    private int resumoCount;
    private String resumoDate;
    private TaskRepository taskRepository;
    private ProgressBar progressBar;
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
        progressBar = findViewById(R.id.splash_create_task);

        // DATABASE FIREBASE
        databaseReference = FirebaseFirestore.getInstance();

        // EVENTO DE CLIQUE PARA ADICIONAR A TAREFA
        btnAdicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarTarefa( edtTituloTarefa.getText().toString().trim(),  edtNotasTarefa.getText().toString().trim(), dataInicial, dataFinal );
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
        edtHoraInicio.setInputType(InputType.TYPE_NULL);
        edtHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(edtHoraInicio);
            }
        });
        edtHoraFim.setInputType(InputType.TYPE_NULL);
        edtHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDate(edtHoraFim);
            }

        });
    }
    public void showDate(EditText dateButton){
        Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(CreateTask.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateButton.setText(dayOfMonth + "/" + (((monthOfYear + 1) < 10) ? ("0" + (monthOfYear + 1)) : (monthOfYear + 1)) + "/" + year);
                        Calendar selectedDate = Calendar.getInstance();
                        // Atualizar o valor da variável de instância
                        if (dateButton == edtHoraInicio) {
                            selectedDate.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                            dataInicial = selectedDate.getTime();
                        } else if (dateButton == edtHoraFim) {
                            selectedDate.set(year, monthOfYear, dayOfMonth, 23, 59, 59);
                            dataFinal = selectedDate.getTime();
                        }
                    }
                }, year, month, day);
        picker.show();
    };

    private void adicionarTarefa(String titulo, String notas, Date dataInicio, Date dataFinal) {
        taskRepository = new TaskRepository();
        // VERIFICA SE TODOS OS CAMPOS ESTÃO PREENCHIDOS
        if (titulo.isEmpty() || dataInicio == null || dataFinal == null) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        btnAdicionarTarefa.setEnabled(false);
        btnAdicionarTarefa.setText("");
        progressBar.setVisibility(View.VISIBLE);
        // OBTEM A PRIORIDADE ESCOLHIDA
        int idPrioridadeSelecionada = radioGroupPrioridade.getCheckedRadioButtonId();
        RadioButton prioridadeSelecionada = findViewById(idPrioridadeSelecionada);

        // LOG TESTAR
        Log.d("DEBUG", "Prioridade Selecionada: " + prioridadeSelecionada.getText().toString());

        resumoCount = 0;
        resumoDate = "";
        // CHAMANDO INSTANCIA DA CLASSE TAREFAS
        Task tarefa = new Task("", titulo, notas, dataInicio, dataFinal, getCodigoPrioridade(prioridadeSelecionada.getText().toString()), getUid(), 0,resumoCount, resumoDate);
        taskRepository.createTask(tarefa , new TaskFirebaseDataSource.TaskListener(){
            @Override
            public void onSuccess() {
                btnAdicionarTarefa.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(CreateTask.this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show(); // ALERT
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish(); // FECHA A ATIVIDADE APOS ADICIONAR A TAREFA
            }
            @Override
            public void onFailure(String errorMessage) {
                btnAdicionarTarefa.setEnabled(true);
                btnAdicionarTarefa.setText("Adiconar");
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });



    }
    public String getUid(){
        // PEGANDO O UID DO USUÁRIO
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user  = myAuth.getCurrentUser();
        String userId = user.getUid();
        return userId;
    }

    private int getCodigoPrioridade(String prioridade) {
        // RETORNA QUAL É A PRIORIDADE ESCOLHIDA
        if ("Alta".equals(prioridade)) return 1;
        if ("Média".equals(prioridade)) return 2;
        if ("Baixa".equals(prioridade)) return 3;
        return 0;
    }
}
