package com.devlist.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devlist.app.auth.LoginUser;
import com.devlist.app.screens.user.RegisterUser;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Bem_vindo extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bem_vindo, container, false);

        view.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                abrirLogin();
            }
        });

        view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                abrirCadastro();
            }
        });

        return view;
    }

    private void abrirLogin() {
        Intent intent = new Intent(getActivity(), LoginUser.class);
        startActivity(intent);
        dismiss();
    }

    private void abrirCadastro() {
        Intent intent = new Intent(getActivity(), RegisterUser.class);
        startActivity(intent);
        dismiss();
    }
}
