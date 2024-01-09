package com.devlist.app.screens.splash_screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devlist.app.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen1 extends AppCompatActivity {

    //Criando variáveis
    ProgressBar splash_carregamento;
    Timer timer;
    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);

        splash_carregamento = findViewById(R.id.splash_carregamento);

        //Atualiza a barra de progresso e quando termina o tempo, muda para a SplashScreen2
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //a cada 100 milissegundos splash_carregamento é incrementada em 1
                cont++;
                splash_carregamento.setProgress(cont);
                if (cont == 30) {
                    startActivity(new Intent(SplashScreen1.this, SplashScreen2.class));
                    finish();
                    timer.cancel();
                }
            }
        };

        //Instancia o Timer e agenda a tarefa para ser executada a cada 100 milissegundos (0 inicialmente).
        timer = new Timer();
        timer.schedule(timerTask, 0, 100);
    }
}
