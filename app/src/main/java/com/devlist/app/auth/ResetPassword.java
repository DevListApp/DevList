package com.devlist.app.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devlist.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;


public class ResetPassword extends AppCompatActivity {

    Button btnResetPasswor, btnBackFogout;
    EditText email;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_reset_password);

        btnResetPasswor = findViewById(R.id.btnResetPasswor);
        btnBackFogout = findViewById(R.id.btnBackFogout);
        email = findViewById(R.id.email);

        btnResetPasswor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFields()) {
                    resetPassword(email.getText().toString().trim());
                }
            }
        });

        btnBackFogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void resetPassword(String email){
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //caso der tudo certo vai executar esse
                Toast.makeText(ResetPassword.this, "Email enviado!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //caso acontece algum erro vai executar essa sentença
                Toast.makeText(ResetPassword.this, "Email não enviado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkAllFields() {
        if(email.getText().toString().isEmpty()){
            email.setError("Infome o email!");
            email.requestFocus();
            return false;
        }else{
            return true;
        }
    }
}