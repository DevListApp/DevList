package com.devlist.app.screens.dashboard;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
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
    private  TaskAdapter taskAdapter;
    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userName = findViewById(R.id.userName);
        userRepository = new UserRepository();


        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        userName.setText(viewModel.loadUser());
        Log.d(TAG, "Loading Activity Dashboard");



        //Observar mudanças na lista de tarefas
       viewModel.getTaskList().observe(this, new Observer<List<Task>>() {
           @Override
           public void onChanged(List<Task> tasks) {
               taskAdapter.updateTaskList(tasks);
           }
       });

        //Observar mudanças na tarefa selecionada
        viewModel.getSelectedTask().observe(this, selectedTask -> {
            if (selectedTask != null) {
                // Iniciar a nova atividade com os dados da tarefa selecionada
                Intent intent = new Intent(getApplicationContext(), UpdateTask.class);
                intent.putExtra("task_data", selectedTask);
                startActivity(intent);
                // Limpar a tarefa selecionada após a navegação
                viewModel.selectTask(null);
                finish();
            }
        });

        //Inicializando o recyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDashboard);
        taskAdapter = new TaskAdapter(new ArrayList<>(), viewModel);

        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnHomePage  = findViewById(R.id.homePageBtn);
        btnAddTarefa = findViewById(R.id.btnAddTarefa);
        profile = findViewById(R.id.btnProfile);

        btnAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateTask.class);
                startActivity(intent);
                finish();
            }
        });

        btnHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.logoutUser();
                Intent intent = new Intent(getApplicationContext(), SplashScreen2.class);
                startActivity(intent);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userRepository.logoutUser();
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
            }
        });

    }


}