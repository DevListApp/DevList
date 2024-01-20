package com.devlist.app.screens.splash_screens;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.devlist.app.R;

public class SplashScreen2 extends AppCompatActivity {

    // Método chamado ao criar a atividade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Definir o layout da atividade como a segunda tela de introdução
        setContentView(R.layout.activity_splash_screen2);
        // Chamar método para exibir o BottomSheet (tela de boas-vindas)
        exibirBottomSheet();
    }

    // Método para exibir o BottomSheet (tela de boas-vindas)
    private void exibirBottomSheet() {
        // Obter o gerenciador de fragmentos da atividade
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Criar uma instância da classe Bem_vindo, que é um BottomSheetDialogFragment
        Bem_vindo bottomSheetFragment = new Bem_vindo();

        // Verificar se o fragmento já foi adicionado para evitar a sobreposição
        if (!bottomSheetFragment.isAdded()) {
            // Mostrar o BottomSheetFragment usando o gerenciador de fragmentos
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());
        }
    }
}
