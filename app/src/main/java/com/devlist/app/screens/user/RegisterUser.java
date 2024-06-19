// Declaração do pacote para a classe
package com.devlist.app.screens.user;

// Declarações de importação para bibliotecas e classes necessárias

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devlist.app.R;
import com.devlist.app.auth.LoginUser;
import com.devlist.app.screens.splash_screens.SplashScreen2;
import com.devlist.app.data.sources.UserFirebaseDataSource;

import java.util.ArrayList;
import java.util.List;

// Definição da classe para a atividade de registro de usuário
public class RegisterUser extends AppCompatActivity {
    // Declaração de elementos de interface do usuário (UI)
    Button btnBackRegister, btnRegisterUser, btnViewLogin;
    EditText registerName, registerEmail, registerPassword, confirmPassword;
    ProgressBar determinateBar;
//    progress_strong_password -> variável que contem o valor da barra de progresso

    // Anotação indicando que o código a seguir pode ignorar um erro de lint específico
    @SuppressLint("MissingInflatedId")
    // Método chamado quando a atividade é criada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout para esta atividade
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_register_user);

        // Cria uma instância da classe ViewModelUser usando ViewModelProvider
        final ViewModelUser viewModelUser = new ViewModelProvider(this).get(ViewModelUser.class);

        // Inicializa os elementos de interface do usuário encontrando suas visualizações correspondentes
        btnBackRegister = findViewById(R.id.btnBackRegister);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnViewLogin = findViewById(R.id.btnViewLogin);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        //valor da poncentagem de senha forte
        determinateBar = findViewById(R.id.determinateBar);
        determinateBar.setMax(100);
        determinateBar.setProgress(0);

        // Define um ouvinte de clique para o botão "Voltar"
        btnBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um intent para navegar até SplashScreen2
               Intent intent = new Intent(getApplicationContext(), SplashScreen2.class);
               // Inicia a atividade SplashScreen2
                startActivity(intent);
                finish();
            }
        });

        // Define um ouvinte de clique para o botão "Registrar"
        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se as senhas coincidem
                if (checkPassword()) {
                    // Verifica se todos os campos obrigatórios estão preenchidos
                    if (checkAllFields()) {
                        // Chama o método createUser de ViewModelUser, passando informações do usuário e um retorno de chamada
                        viewModelUser.creatUser(userInformation(), new UserFirebaseDataSource.UserCreationListener() {
                            @Override
                            public void onSuccess() {
                                // Manipula a criação bem-sucedida do usuário
                                Toast.makeText(RegisterUser.this, "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                // Manipula a falha na criação do usuário e exibe uma mensagem de erro
                                Toast.makeText(RegisterUser.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        // Define um ouvinte de clique para o botão "Login"
        btnViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um intent para navegar até LoginUser
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                // Inicia a atividade LoginUser
                startActivity(intent);
                // Finaliza a atividade atual
                finish();
            }
        });

        //métodos são chamados sempre que o texto deste TextView é alterado.
        registerPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                int strength = calculatePasswordStrength(password);
                determinateBar.setProgress(strength);
            }
        });

    }

    private int calculatePasswordStrength(String password) {
        int strength = 0;

        // Critério 1: Tamanho da senha
        if (password.length() >= 8) {
            strength += 20; // Incrementa 20 pontos para tamanho suficiente
        }

        // Critério 2: Letras maiúsculas
        if (password.matches(".*[A-Z].*")) {
            strength += 20; // Incrementa 20 pontos para pelo menos uma letra maiúscula
        }

        // Critério 3: Letras minúsculas
        if (password.matches(".*[a-z].*")) {
            strength += 20; // Incrementa 20 pontos para pelo menos uma letra minúscula
        }

        // Critério 4: Dígitos
        if (password.matches(".*\\d.*")) {
            strength += 20; // Incrementa 20 pontos para pelo menos um dígito
        }

        // Critério 5: Caracteres especiais
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            strength += 20; // Incrementa 20 pontos para pelo menos um caractere especial
        }

        return strength;
    }

    // Método para recuperar as informações do usuário dos campos de entrada e retornar como uma lista
    private List<String> userInformation() {
        List<String> user = new ArrayList<>();
        user.add(registerName.getText().toString());
        user.add(registerEmail.getText().toString());
        user.add(registerPassword.getText().toString());
        user.add(confirmPassword.getText().toString());
        return user;
    }

    // Método para verificar se as senhas coincidem e exibir um erro se não
    private boolean checkPassword() {
        if (!registerPassword.getText().toString()
                .equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Senhas não coincidem!");
            return false;
        }
        return true;
    }

    // Método para verificar se todos os campos obrigatórios estão preenchidos e exibir erros se não
    private boolean checkAllFields() {
        if (registerName.getText().toString().isEmpty()) {
            registerName.setError("Informe o nome");
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
