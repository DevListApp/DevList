package com.devlist.app;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devlist.app.auth.LoginUser;

public class MainActivity extends AppCompatActivity {

    Button viewRegister, viewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewRegister = findViewById(R.id.viewRegister);
        viewLogin = findViewById(R.id.viewLogin);

        viewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(intent);
                finish();
            }
        });

        viewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
                finish();
            }
        });

    }

}



