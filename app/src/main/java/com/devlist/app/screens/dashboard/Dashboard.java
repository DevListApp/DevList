package com.devlist.app.screens.dashboard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlist.app.R;
import com.devlist.app.data.models.Task;
import com.devlist.app.screens.task.CreateTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    TextView userName;
    BottomNavigationView menu_bottom;
    private List<Task> taskList;
    private  TaskAdapter taskAdapter;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userName = findViewById(R.id.userName);
        menu_bottom = findViewById(R.id.menu_bottom);

        menu_bottom.setItemIconTintList(null);
        menu_bottom.setItemBackground(null);
        menu_bottom.setAnimation(null);

        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        userName.setText(viewModel.loadUser());

        //Inicializando o recyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDashboard);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Inicializar o Firestore
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Obter dados da coleção "task"
        Query query = firebaseFirestore.collection("tasks");
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                taskList.clear(); // Limpar a lista atual
                for (Task model : task.getResult().toObjects(Task.class)) {
                    taskList.add(model);
                }
                taskAdapter.notifyDataSetChanged(); // Notificar o RecyclerView sobre as mudanças nos dados
            } else {
                Log.d(TAG, "Deu merda aqui!");
                // Tratar erro ao obter dados
            }
        });

        menu_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.page_1) {
                    //logica para essas opções
                    return true;
                }else if (item.getItemId() == R.id.page_2) {
                    return true;
                }else if (item.getItemId() == R.id.page_3) {
                    Intent intent = new Intent(getApplicationContext(), CreateTask.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (item.getItemId() == R.id.page_4) {
                    return true;
                }else if (item.getItemId() == R.id.page_5) {
                    return true;
                }
                return false;
            }
        });
    }
}
