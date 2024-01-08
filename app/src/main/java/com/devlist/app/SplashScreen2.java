package com.devlist.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public class SplashScreen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        exibirBottomSheet();
    }

    private void exibirBottomSheet() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bem_vindo bottomSheetFragment = new Bem_vindo();
        if (!bottomSheetFragment.isAdded()) {
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());
        }
    }
}