package com.devlist.app.auth; // Pacote onde está localizada a classe

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devlist.app.R; // Importações necessárias
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    Button btnResetPasswor, btnBackFogout; // Declaração de variáveis para os botões
    EditText email; // Declaração de variável para o campo de email

    FirebaseAuth auth = FirebaseAuth.getInstance(); // Instância do FirebaseAuth para redefinição de senha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_reset_password); // Define o layout da atividade

        // Vinculação de variáveis aos componentes da interface do usuário
        btnResetPasswor = findViewById(R.id.btnResetPasswor);
        btnBackFogout = findViewById(R.id.btnBackFogout);
        email = findViewById(R.id.email);

        // Configuração de OnClickListener para o botão de redefinir senha
        btnResetPasswor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFields()) {
                    resetPassword(email.getText().toString().trim());
                }
            }
        });

        // Configuração de OnClickListener para o botão de voltar
        btnBackFogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Método para redefinir a senha do usuário
    public void resetPassword(String email){
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // Se o email for enviado com sucesso, exibe uma mensagem
                Toast.makeText(ResetPassword.this, "Email enviado!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Se ocorrer uma falha ao enviar o email, exibe uma mensagem de erro
                Toast.makeText(ResetPassword.this, "Email não enviado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para verificar se todos os campos estão preenchidos
    private boolean checkAllFields() {
        if(email.getText().toString().isEmpty()){
            email.setError("Informe o email!");
            email.requestFocus();
            return false;
        }else{
            return true;
        }
    }
}
