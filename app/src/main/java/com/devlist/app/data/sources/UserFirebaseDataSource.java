package com.devlist.app.data.sources; // Pacote onde está localizada a classe

import static android.content.ContentValues.TAG; // Importação estática de TAG para uso posterior

import android.util.Log; // Importação de classes necessárias

import androidx.annotation.NonNull; // Importação de anotações necessárias

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

public class UserFirebaseDataSource {
    private final FirebaseAuth authUser = FirebaseAuth.getInstance(); // Instância do FirebaseAuth para autenticação do usuário

    // Método para obter o usuário autenticado
    public FirebaseUser getUserAuthenticator(){
        return authUser.getCurrentUser();
    }

    // Interface para lidar com callbacks de criação de usuário
    public interface UserCreationListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    // Método para criar um novo usuário no Firebase Authentication
    public void createUserAuth(List<String> user, UserCreationListener listener){
        authUser.createUserWithEmailAndPassword(user.get(1), user.get(2))
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser authCurrentUser = authUser.getCurrentUser();

                        //Adicionando nome ao usuário
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(user.get(0))
                                .build();

                        authCurrentUser.updateProfile(profileUpdate); // Atualiza o perfil do usuário com o nome fornecido
                        getUserAuthenticator(); // Obtém novamente o usuário autenticado para garantir a atualização do perfil
                        listener.onSuccess(); // Notifica o ouvinte de sucesso na criação do usuário
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "createUserWithEmail:failure", e);
                        // Trata diferentes tipos de exceções que podem ocorrer durante a criação do usuário
                        if (e instanceof FirebaseAuthWeakPasswordException) {
                            listener.onFailure("Senha fraca. A senha deve ter pelo menos 6 caracteres.");

                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            listener.onFailure("Insira um email válido");

                        } else if (e instanceof FirebaseAuthUserCollisionException){
                            listener.onFailure("Email já cadastrado!");

                        } else {
                            listener.onFailure("Falha ao cadastrar usuário.");
                        }
                    }
                });
    }

    // Método para fazer logout do usuário
    public void logoutUser(){
        authUser.signOut(); // Encerra a sessão do usuário
    }
}
