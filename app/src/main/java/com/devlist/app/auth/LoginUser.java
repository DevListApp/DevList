package com.devlist.app.auth; // Pacote onde está localizada a classe

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG; // Importação estática de TAG para uso posterior

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devlist.app.screens.dashboard.Dashboard;
import com.devlist.app.screens.splash_screens.SplashScreen2;
import com.devlist.app.R; // Importações de classes necessárias
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {

    // Declaração de variáveis para os componentes da interface do usuário
    EditText loginEmail, loginPassword;
    Button btnBackLogin;
    Button btnLoginUser;
    Button btnForgoutUser;
    FirebaseAuth authUser = FirebaseAuth.getInstance(); // Instância do FirebaseAuth para autenticação de usuário
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_login_user); // Define o layout da atividade

        // Muda a cor da barra de status se estiver na tela de login
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // Vinculação de variáveis aos componentes da interface do usuário
        btnBackLogin = findViewById(R.id.btnBackLogin);
        btnLoginUser = findViewById(R.id.btnLoginUser);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btnForgoutUser = findViewById(R.id.btnForgoutUser);
        progressBar = findViewById(R.id.splash_login);

        // Configuração de OnClickListener para o botão de voltar
        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SplashScreen2.class);
                startActivity(intent);
                finish();
            }
        });

        // Configuração de OnClickListener para o botão de login
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFields()) {
                    btnLoginUser.setEnabled(false);
                    btnLoginUser.setText("");
                    progressBar.setVisibility(View.VISIBLE);
                    authLoginUser(loginEmail.getText().toString(), loginPassword.getText().toString());
                }
            }
        });

        // Configuração de OnClickListener para o botão de esqueci a senha
        btnForgoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Método para verificar se todos os campos estão preenchidos
    private boolean checkAllFields() {
        if (loginEmail.getText().toString().isEmpty()) {
            loginEmail.setError("Informe o email para fazer login!");
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

    // Método para autenticar o usuário
    public void authLoginUser(String email, String password){
        authUser.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnLoginUser.setEnabled(true);
                        btnLoginUser.setText("Entrar");
                        if (task.isSuccessful()) {
                            // Se o login for bem-sucedido, atualiza a interface do usuário e redireciona para a tela principal
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = authUser.getCurrentUser();
                            Toast.makeText(LoginUser.this, "Sucesso ao fazer login!.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Se o login falhar, exibe uma mensagem de erro e limpa os campos de email e senha
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
