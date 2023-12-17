package com.devlist.app;

import static android.content.ContentValues.TAG;

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

import com.devlist.app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    Button btnBackRegister, btnRegisterUser;
    EditText registerName, registerEmail, registerPassword, confirmPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth userAuth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnBackRegister = findViewById(R.id.btnBackRegister);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword);


        btnBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPassword()) {
                    if(checkAllFields()) {
                        User user = new User(registerName.getText().toString(), registerEmail.getText().toString(),
                                registerPassword.getText().toString(), confirmPassword.getText().toString());
                        createUserAuth(user);
                    }
                }

            }
        });
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

    public void createUserAuth(User user) {
        userAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser authCurrentUser = userAuth.getCurrentUser();
                            createUser(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            // Tratar o erro de senha fraca aqui
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(RegisterUser.this, "Senha fraca. A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show();

                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(RegisterUser.this, "Insira um email válido", Toast.LENGTH_SHORT).show();

                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegisterUser.this, "Email já cadastrado!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterUser.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    public void createUser(User user) {
        Map<String, String> name = new HashMap<>();
        name.put("name", user.getName());
        // Adiciona o usuário ao Firestore
        db.collection("usuario")
                .document(user.getName())
                .set(name)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        try {
                            Toast.makeText(RegisterUser.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Documento adicionado com sucesso");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterUser.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Erro ao adicionar documento", e);
                    }
                });
    }

}