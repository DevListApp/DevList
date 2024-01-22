package com.devlist.app.screens.dashboard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlist.app.R;
import com.devlist.app.auth.LoginUser;
import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.UserRepository;
import com.devlist.app.screens.splash_screens.Bem_vindo;
import com.devlist.app.screens.splash_screens.SplashScreen2;
import com.devlist.app.screens.task.CreateTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    TextView userName;
    BottomNavigationView menu_bottom;
    ImageButton btnHomePage, profile, btnAddTarefa;
    private List<Task> taskList;
    private  TaskAdapter taskAdapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth userAuth;

    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userName = findViewById(R.id.userName);
        userRepository = new UserRepository();

//        menu_bottom.setItemIconTintList(null);
//        menu_bottom.setItemBackground(null);
//        menu_bottom.setAnimation(null);

        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        userName.setText(viewModel.loadUser());

        //Inicializando o recyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDashboard);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Inicializar o Firestore
        userAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser user  = userAuth.getCurrentUser();
        String userId = user.getUid();

        // Obter dados da coleção "task"
        CollectionReference storiesCollection = firebaseFirestore.collection("tasks");
        // Query de consulta no banco de dados
        Query query =  storiesCollection.whereEqualTo("auth", userId);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                taskList.clear(); // Limpar a lista atual
                for (Task model : task.getResult().toObjects(Task.class)) {
                    taskList.add(model);
                }
                taskAdapter.notifyDataSetChanged(); // Notificar o RecyclerView sobre as mudanças nos dados
            } else {
                Toast.makeText(this, "Erro ao carregar tarefas!", Toast.LENGTH_SHORT).show();
            }
        });

//        menu_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.page_1) {
//                    //logica para essas opções
//                    return true;
//                }else if (item.getItemId() == R.id.page_2) {
//                    return true;
//                }else if (item.getItemId() == R.id.page_3) {
//                    Intent intent = new Intent(getApplicationContext(), CreateTask.class);
//                    startActivity(intent);
//                    finish();
//                    return true;
//                }else if (item.getItemId() == R.id.page_4) {
//                    return true;
//                }else if (item.getItemId() == R.id.page_5) {
//                    return true;
//                }
//                return false;
//            }
//        });
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

    }


}