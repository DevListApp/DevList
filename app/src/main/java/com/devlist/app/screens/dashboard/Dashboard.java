package com.devlist.app.screens.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devlist.app.R;
import com.devlist.app.screens.splash_screens.SplashScreen2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView menu_bottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        menu_bottom = findViewById(R.id.menu_bottom);
        menu_bottom.setItemIconTintList(null);
        menu_bottom.setItemBackground(null);
        menu_bottom.setAnimation(null);




        menu_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.page_1) {
                    //logica para essas opções
                    return true;
                } else if (item.getItemId() == R.id.page_2) {

                    return true;
                }else if (item.getItemId() == R.id.page_3) {

                    return true;
                }
                else if (item.getItemId() == R.id.page_4) {

                    return true;
                }else if (item.getItemId() == R.id.page_5) {

                    return true;
                }

                return false;
            }
        });
    }
}
