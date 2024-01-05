package com.devlist.app.auth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devlist.app.MainActivity;
import com.devlist.app.MainListActivity;
import com.devlist.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {

    //cria as variáveis
    EditText loginEmail, loginPassword;
    Button btnBackLogin;
    Button btnLoginUser;
    Button btnForgoutUser;
    FirebaseAuth authUser = FirebaseAuth.getInstance(); //instacia auth do firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user); //Carrega essa activity

        //Muda cor do status bar se estiver na activity de login
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }




        FirebaseUser currentUser = authUser.getCurrentUser();
        // Se o usuário estiver autenticado
        if(currentUser != null) {
            startActivity(new Intent(this, MainListActivity.class));
            finish();
        }

        btnBackLogin = findViewById(R.id.btnBackLogin);//atribuí as variáveis aos compoenntes
        btnLoginUser = findViewById(R.id.btnLoginUser);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btnForgoutUser = findViewById(R.id.btnForgoutUser);

        btnBackLogin.setOnClickListener(new View.OnClickListener() { //leitor de eventos no botão voltar <-
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLoginUser.setOnClickListener(new View.OnClickListener() { //leitor de eventos no botão de login
            @Override
            public void onClick(View v) {   //função de click
                if(checkAllFields()) {
                    authLoginUser(loginEmail.getText().toString(), loginPassword.getText().toString()); //chama e passa os paremetros para a função de fazer login
                }
            }
        });

        btnForgoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(intent);
                finish();
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
//                      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
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