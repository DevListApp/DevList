package com.devlist.app.screens.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devlist.app.R;
import com.devlist.app.auth.LoginUser;
import com.devlist.app.screens.dashboard.TaskAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    Button btnBackSystem;
    TextView nameUser;
    private FirebaseAuth currentUser;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        btnBackSystem =  findViewById(R.id.btnBackSystem);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        nameUser = findViewById(R.id.nameUser);

        //Criar dados mockados de exemplo
        List<String> itemList = new ArrayList<>();
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");

        //Iniciar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TaskAdapter adapter = new TaskAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        nameUser.setText(homeViewModel.getNameUser());
        // Create the observer which updates the UI.

        btnBackSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = FirebaseAuth.getInstance();
                currentUser.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
    }
}