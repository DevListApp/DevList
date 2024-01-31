package com.devlist.app.screens.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devlist.app.R;
import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.TaskRepository;
import com.devlist.app.data.sources.TaskFirebaseDataSource;
import com.devlist.app.screens.dashboard.Dashboard;
import com.devlist.app.screens.dashboard.TaskAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateTask extends AppCompatActivity {
    private ProgressBar progressBar, progressBarRemove;
    private Button btnBackEditTask, btnUpdateTask, btnRemoveTask;
    private EditText titleTask, descriptionTask, dt_start_scheduled, dt_end_scheduled;
    private RadioButton heightPriority, midllePriority, lowPriority;
    private RadioGroup radioGroup;
    private TaskRepository taskRepository;
    private DatePickerDialog picker;
    private Date dataInicial;
    private Date dataFinal;
    private Task taskUpdate, selectedTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_update_task);

        btnBackEditTask = findViewById(R.id.btnBackEditTask);
        titleTask = findViewById(R.id.taskEditTitulo);
        descriptionTask = findViewById(R.id.taskEditNote);
        dt_start_scheduled = findViewById(R.id.taskDtInitialEdit);
        dt_end_scheduled = findViewById(R.id.taskDtFinalEdit);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        progressBar = findViewById(R.id.splash_update_task);
        progressBarRemove = findViewById(R.id.splash_remove_task);
        heightPriority = findViewById(R.id.taskHeightUpdatePriority);
        midllePriority = findViewById(R.id.taskMidlleUpdatePriority);
        lowPriority = findViewById(R.id.taskLowUpdatePriority);
        radioGroup = findViewById(R.id.taskUpdatePriority);
        btnRemoveTask = findViewById(R.id.btnRemoveTask);

        btnBackEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        // Recuperar dados do intent
        Intent intent = getIntent();
        selectedTask = intent.getParcelableExtra("task_data");
        taskRepository = new TaskRepository();

        // Exibir os dados da tarefa na nova atividade
        if (selectedTask != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            titleTask.setText(selectedTask.getTitulo());
            descriptionTask.setText(selectedTask.getNotas());
            dt_start_scheduled.setText(dateFormat.format(selectedTask.getHoraInicio()));
            dt_end_scheduled.setText(dateFormat.format(selectedTask.getHoraFim()));
            dataInicial = selectedTask.getHoraInicio();
            dataFinal = selectedTask.getHoraFim();
            if(selectedTask.getPrioridade() == 1){
                heightPriority.setChecked(true);
            } else if(selectedTask.getPrioridade() == 2){
                midllePriority.setChecked(true);
            } else {
                lowPriority.setChecked(true);
            }
        }

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateTask.setEnabled(false);
                btnUpdateTask.setText("");
                progressBar.setVisibility(View.VISIBLE);
                // VERIFICA SE TODOS OS CAMPOS ESTÃO PREENCHIDOS
                if (titleTask.getText().toString().isEmpty() || descriptionTask.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    btnUpdateTask.setEnabled(true);
                    btnUpdateTask.setText("Atualizar");
                    progressBar.setVisibility(View.INVISIBLE);
                    return ;
                }
                // VERIFICA SE DATA INICIAL É MAIOR QUE DATA FINAL
                if(dataInicial.after(dataFinal)) {
                    Toast.makeText(getApplicationContext(), "Datas inválidas!", Toast.LENGTH_SHORT).show();
                    btnUpdateTask.setEnabled(true);
                    btnUpdateTask.setText("Atualizar");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                taskRepository.updateTask(getInformationTask(), new TaskFirebaseDataSource.TaskListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UpdateTask.this, "Sucesso ao atualizar tarefa", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish();

                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(UpdateTask.this, errorMessage, Toast.LENGTH_SHORT).show();
                        btnUpdateTask.setEnabled(true);
                        btnUpdateTask.setText("Atualizar");
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        btnRemoveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRemoveTask.setEnabled(true);
                btnRemoveTask.setText("");
                progressBarRemove.setVisibility(View.VISIBLE);
                taskRepository.deleteTask(selectedTask.getId(), new TaskFirebaseDataSource.TaskListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UpdateTask.this, "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        btnRemoveTask.setEnabled(false);
                        btnRemoveTask.setText("Remover");
                        progressBarRemove.setVisibility(View.INVISIBLE);
                        Toast.makeText(UpdateTask.this, errorMessage, Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        dt_start_scheduled.setInputType(InputType.TYPE_NULL);
        dt_start_scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(dt_start_scheduled);
                selectedTask.setHoraInicio(dataInicial);
            }
        });
        dt_end_scheduled.setInputType(InputType.TYPE_NULL);
        dt_end_scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(dt_end_scheduled);
                selectedTask.setHoraFim(dataFinal);
            }

        });

    }
    public void showDate(EditText dateButton){
        Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(UpdateTask.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateButton.setText(dayOfMonth + "/" + (((monthOfYear + 1) < 10) ? ("0" + (monthOfYear + 1)) : (monthOfYear + 1)) + "/" + year);
                        Calendar selectedDate = Calendar.getInstance();
                        // Atualizar o valor da variável de instância
                        if (dateButton == dt_start_scheduled) {
                            selectedDate.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                            dataInicial = selectedDate.getTime();
                        } else if (dateButton == dt_end_scheduled) {
                            selectedDate.set(year, monthOfYear, dayOfMonth, 23, 59, 59);
                            dataFinal = selectedDate.getTime();
                        }
                    }
                }, year, month, day);
        picker.show();
    };

    public Task getInformationTask(){
        int idPrioridadeSelecionada = radioGroup.getCheckedRadioButtonId();
        RadioButton prioridadeSelecionada = findViewById(idPrioridadeSelecionada);
        taskUpdate = new Task(selectedTask.getId(), titleTask.getText().toString(), descriptionTask.getText().toString(), dataInicial, dataFinal, getCodigoPrioridade(prioridadeSelecionada.getText().toString()), selectedTask.getAuth(), selectedTask.getConcluido(), selectedTask.getResumoCount(), selectedTask.getResumoDate());
        return  taskUpdate;
    }
    private int getCodigoPrioridade(String prioridade) {
        // RETORNA QUAL É A PRIORIDADE ESCOLHIDA
        if ("Alta".equals(prioridade)) return 1;
        if ("Média".equals(prioridade)) return 2;
        if ("Baixa".equals(prioridade)) return 3;
        return 0;
    }

}