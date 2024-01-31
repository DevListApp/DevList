package com.devlist.app.screens.profile;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devlist.app.R;
import com.devlist.app.data.repositories.UserRepository;
import com.devlist.app.screens.dashboard.Dashboard;
import com.devlist.app.screens.splash_screens.SplashScreen2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;

public class Profile extends AppCompatActivity {
    TextView userName, inputEmail, inputResumo;

    Button btn_sair, btnBackProfile;

    private UserRepository userRepository;
    private FirebaseAuth userAuth;
    private ProfileViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.userName);
        inputEmail = findViewById(R.id.inputEmail);
        btn_sair = findViewById(R.id.btn_sair);
        inputResumo = findViewById(R.id.inputResumo);
        btnBackProfile = findViewById(R.id.btnBackProfile);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        userRepository = new UserRepository();
        userAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        FirebaseUser user  = userAuth.getCurrentUser();
        String userId = user.getUid();

        // Chamar a função para obter o resumo dos documentos
        filterResumo(userId);

        userName.setText(viewModel.loadUser());
        inputEmail.setText(viewModel.loadEmail());

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.logoutUser();
                Intent intent = new Intent(getApplicationContext(), SplashScreen2.class);
                startActivity(intent);
                finish();
            }
        });

        btnBackProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterResumo(String userId) {
        LocalDate dataAtual = LocalDate.now();
        String dataAtualString = dataAtual.toString();
        CollectionReference storiesCollection = FirebaseFirestore.getInstance().collection("tasks");
        Query query = storiesCollection.whereEqualTo("auth", userId)
                .whereEqualTo("resumoDate", dataAtualString)
                .whereEqualTo("resumoCount", 1);

        // Executa a consulta
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    int count = querySnapshot.size();
                    System.out.println("Número de tarefas concluídas hoje: " + count);
                    inputResumo.setText("Número de tarefas concluídas hoje: " + count);
                } else {
                    System.out.println("Nenhum documento correspondente encontrado.");
                }
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    System.out.println("Erro ao executar a consulta: " + exception.getMessage());
                }
            }
        });
    }
}
