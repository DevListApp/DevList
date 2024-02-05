package com.devlist.app.data.sources;

import static android.content.ContentValues.TAG;
import android.util.Log;
import androidx.annotation.NonNull;
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
    private final FirebaseAuth authUser = FirebaseAuth.getInstance();

    public FirebaseUser getUserAuthenticator(){
        return authUser.getCurrentUser();
    }

    public interface UserCreationListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }


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

                    authCurrentUser.updateProfile(profileUpdate);
                    getUserAuthenticator();
                    listener.onSuccess();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "createUserWithEmail:failure", e);
                    // Tratar o erro de senha fraca aqui
                    if (e instanceof FirebaseAuthWeakPasswordException) {
                        listener.onFailure("Senha fraca. A senha deve ter pelo menos 6 caracteres.");

                    } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        listener.onFailure("Insira um email válido");

                    } else if (e instanceof FirebaseAuthUserCollisionException){
                        listener.onFailure("Email já cadastrado!");
                    }
                    else {
                        listener.onFailure("Falha ao cadastrar usuário.");
                    }
                }
            });
    }

    public void logoutUser(){
        authUser.signOut();
    }



}
