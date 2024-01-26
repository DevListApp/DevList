package com.devlist.app.screens.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devlist.app.R;
import com.devlist.app.screens.dashboard.Dashboard;

public class UpdateTask extends AppCompatActivity {
    private Button btnBackEditTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        btnBackEditTask = findViewById(R.id.btnBackEditTask);

        btnBackEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });



    }
}