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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    Button btnBackRegister;
    Button btnRegisterUser;
    EditText registerName;
    EditText registerEmail;
    EditText registerPassword;
    EditText confirmPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        constructor(registerName.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString());
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

    public void constructor(String name, String email, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("nome", name);
        user.put("email", email);
        user.put("senha", password);

        db.collection("usuario")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegisterUser.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterUser.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}