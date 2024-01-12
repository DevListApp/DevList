package com.devlist.app.screens.splash_screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devlist.app.R;
import com.devlist.app.auth.LoginUser;
import com.devlist.app.screens.dashboard.Dashboard;
import com.devlist.app.screens.user.RegisterUser;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Bem_vindo extends BottomSheetDialogFragment {
    FirebaseAuth authUser = FirebaseAuth.getInstance();

    // Método chamado para criar a exibição da tela de boas-vindas
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar o layout da tela de boas-vindas
        View view = inflater.inflate(R.layout.activity_bem_vindo, container, false);

        FirebaseUser currentUser = authUser.getCurrentUser();
        // Se o usuário estiver autenticado
        if(currentUser != null) {
            startActivity(new Intent(getActivity(), Dashboard.class));
            dismiss();
        }

        // Configurar o clique no botão de login
        view.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fechar a tela de boas-vindas e abrir a tela de login
                dismiss();
                abrirLogin();
            }
        });

        // Configurar o clique no botão de registro
        view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fechar a tela de boas-vindas e abrir a tela de cadastro
                dismiss();
                abrirCadastro();
            }
        });

        // Retornar a exibição criada
        return view;
    }

    // Método para abrir a tela de login
    private void abrirLogin() {
        // Criar uma intenção para iniciar a atividade de login
        Intent intent = new Intent(getActivity(), LoginUser.class);
        // Iniciar a atividade e fechar a tela de boas-vindas
        startActivity(intent);
        dismiss();
    }

    // Método para abrir a tela de cadastro
    private void abrirCadastro() {
        // Criar uma intenção para iniciar a atividade de cadastro
        Intent intent = new Intent(getActivity(), RegisterUser.class);
        // Iniciar a atividade e fechar a tela de boas-vindas
        startActivity(intent);
        dismiss();
    }
}
