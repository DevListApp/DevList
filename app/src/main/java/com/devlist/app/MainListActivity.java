package com.devlist.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devlist.app.auth.LoginUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainListActivity extends AppCompatActivity {
    Button btnBackSystem;
    TextView nameUser;
    private FirebaseAuth currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        nameUser = findViewById(R.id.nameUser);

        if(name != null && !"".equals(name)){
            nameUser.setText(name);
        } else {
            nameUser.setText("Nome do usuário");
        }

        //Criar dados mockados de exemplo
        List<String> itemList = new ArrayList<>();
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");
        itemList.add("Item");


        CustomAdapter adapter = new CustomAdapter(itemList);
        //Iniciar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBackSystem =  findViewById(R.id.btnBackSystem);

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