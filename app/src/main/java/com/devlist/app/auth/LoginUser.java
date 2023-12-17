package com.devlist.app.auth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devlist.app.MainActivity;
import com.devlist.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button btnBackLogin, btnLoginUser;
    FirebaseAuth authUser = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        btnBackLogin = findViewById(R.id.btnBackLogin);
        btnLoginUser = findViewById(R.id.btnLoginUser);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFields()) {
                    authLoginUser(loginEmail.getText().toString(), loginPassword.getText().toString());
                }
            }
        });


    }

    private boolean checkAllFields() {
        if (loginEmail.getText().toString().isEmpty()) {
            loginEmail.setError("Infome o email para fazer login!");
            loginEmail.requestFocus();
            return false;
        } else if (loginPassword.getText().toString().isEmpty()) {
            loginPassword.setError("Informe a senha para fazer login!");
            loginPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public void authLoginUser(String email, String password){
        authUser.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = authUser.getCurrentUser();
                            Toast.makeText(LoginUser.this, "Sucesso ao fazer login!.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginUser.this, "Erro ao fazer login, tente novamente!", Toast.LENGTH_SHORT).show();
                            loginEmail.setText("");
                            loginPassword.setText("");
                            loginEmail.requestFocus();
                        }
                    }
                });
    }
}