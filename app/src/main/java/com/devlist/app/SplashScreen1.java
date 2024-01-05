package com.devlist.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devlist.app.auth.LoginUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen1 extends AppCompatActivity {
    ProgressBar splash_carregamento;
    Timer timer;
    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);

        splash_carregamento = findViewById(R.id.splash_carregamento);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                cont++;
                splash_carregamento.setProgress(cont);
                if (cont == 100) {
                    startActivity(new Intent(SplashScreen1.this, LoginUser.class));
                    finish();
                    timer.cancel();
                }
            }
        };


        timer = new Timer();
        timer.schedule(timerTask, 0, 100);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen1.this, LoginUser.class));
                finish();
            }
        }, 3000);
    }
}
