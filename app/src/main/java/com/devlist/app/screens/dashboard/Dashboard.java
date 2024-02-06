package com.devlist.app.screens.dashboard;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlist.app.R;
import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.UserRepository;
import com.devlist.app.screens.profile.Profile;
import com.devlist.app.screens.splash_screens.SplashScreen2;
import com.devlist.app.screens.task.CreateTask;
import com.devlist.app.screens.task.UpdateTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private TextView userName;
    private ImageButton btnHomePage, profile, btnAddTarefa;
    private TaskAdapter taskAdapter;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurando a orientação da tela para retrato
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        // Definindo o layout da atividade
        setContentView(R.layout.activity_dashboard);

        // Inicializando os elementos de UI
        userName = findViewById(R.id.userName);
        btnHomePage = findViewById(R.id.homePageBtn);
        btnAddTarefa = findViewById(R.id.btnAddTarefa);
        profile = findViewById(R.id.btnProfile);
        userRepository = new UserRepository();

        // Obtendo a instância do ViewModel associado a esta atividade
        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Definindo o nome do usuário na TextView
        userName.setText(viewModel.loadUser());

        // Log para registrar o carregamento da atividade no console
        Log.d(TAG, "Loading Activity Dashboard");

        // Observando mudanças na lista de tarefas
        viewModel.getTaskList().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.updateTaskList(tasks); // Atualiza o adaptador com a nova lista de tarefas
            }
        });

        // Observando mudanças na tarefa selecionada
        viewModel.getSelectedTask().observe(this, selectedTask -> {
            if (selectedTask != null) {
                // Inicia a atividade de atualização de tarefa com os dados da tarefa selecionada
                Intent intent = new Intent(getApplicationContext(), UpdateTask.class);
                intent.putExtra("task_data", selectedTask); // Passa os dados da tarefa como extra
                startActivity(intent);
                // Limpa a tarefa selecionada após a navegação
                viewModel.selectTask(null);
                finish();
            }
        });

        // Inicializando o RecyclerView para exibir as tarefas
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDashboard);
        taskAdapter = new TaskAdapter(new ArrayList<>(), viewModel); // Inicializa o adaptador com uma lista vazia de tarefas

        recyclerView.setAdapter(taskAdapter); // Define o adaptador para o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Define o layout do RecyclerView

        // Configurando listeners de clique para os botões
        btnAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a atividade para criar uma nova tarefa
                Intent intent = new Intent(getApplicationContext(), CreateTask.class);
                startActivity(intent);
                finish();
            }
        });

        btnHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Faz logout do usuário e redireciona para a tela de splash
                userRepository.logoutUser();
                Intent intent = new Intent(getApplicationContext(), SplashScreen2.class);
                startActivity(intent);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a tela de perfil do usuário
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
