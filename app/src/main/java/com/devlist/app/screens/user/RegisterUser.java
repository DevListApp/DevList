package com.devlist.app.screens.user;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devlist.app.R;
import com.devlist.app.auth.LoginUser;
import com.devlist.app.data.sources.UserFirebaseDataSource;
import com.devlist.app.screens.splash_screens.SplashScreen2;

import java.util.ArrayList;
import java.util.List;

public class RegisterUser extends AppCompatActivity {
    Button btnBackRegister, btnRegisterUser, btnViewLogin;
    EditText registerName, registerEmail, registerPassword, confirmPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        final ViewModelUser viewModelUser = new ViewModelProvider(this).get(ViewModelUser.class);

        btnBackRegister = findViewById(R.id.btnBackRegister);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnViewLogin = findViewById(R.id.btnViewLogin);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword);

        btnBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SplashScreen2.class);
                startActivity(intent);
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPassword()) {
                    if(checkAllFields()) {
                        viewModelUser.creatUser(userInformation() , new UserFirebaseDataSource.UserCreationListener(){
                            @Override
                            public void onSuccess() {
                                // Atualize a UI de sucesso
                                Toast.makeText(RegisterUser.this, "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(String errorMessage) {
                                // Trate a falha e atualize a UI de acordo
                                Toast.makeText(RegisterUser.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        btnViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private List<String> userInformation(){
        List<String> user = new ArrayList<>();
        user.add(registerName.getText().toString());
        user.add(registerEmail.getText().toString());
        user.add(registerPassword.getText().toString());
        user.add(confirmPassword.getText().toString());
        return user;
    }

    private boolean checkPassword(){
        if(!registerPassword.getText().toString()
                .equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Senhas não coincidem!");
            return false;
        }
        return true;
    }

    private boolean checkAllFields() {
        if (registerName.getText().toString().isEmpty()) {
            registerName.setError("Infome o nome");
            registerName.requestFocus();
            return false;
        } else if (registerEmail.getText().toString().isEmpty()) {
            registerEmail.setError("Informe o email");
            registerEmail.requestFocus();
            return false;
        } else if (registerPassword.getText().toString().isEmpty()) {
            registerPassword.setError("Informe a senha");
            registerPassword.requestFocus();
            return false;
        } else if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Confirme a senha");
            confirmPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }


}