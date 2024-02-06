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

    // Método chamado quando a atividade é criada
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_profile);

        // Inicialização dos elementos de UI
        userName = findViewById(R.id.userName);
        inputEmail = findViewById(R.id.inputEmail);
        btn_sair = findViewById(R.id.btn_sair);
        inputResumo = findViewById(R.id.inputResumo);
        btnBackProfile = findViewById(R.id.btnBackProfile);

        // Inicialização do ViewModel e do repositório de usuário
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        userRepository = new UserRepository();
        userAuth = FirebaseAuth.getInstance(); // Inicialização do FirebaseAuth

        FirebaseUser user  = userAuth.getCurrentUser();
        String userId = user.getUid();

        // Chamar a função para carregar e exibir os dados do usuário
        userName.setText(viewModel.loadUser());
        inputEmail.setText(viewModel.loadEmail());

        // OnClickListener para o botão de sair
        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.logoutUser(); // Desconectar o usuário
                Intent intent = new Intent(getApplicationContext(), SplashScreen2.class); // Iniciar a tela de splash
                startActivity(intent);
                finish(); // Finalizar a atividade atual
            }
        });

        // OnClickListener para o botão de voltar para o dashboard
        btnBackProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class); // Iniciar o dashboard
                startActivity(intent);
                finish(); // Finalizar a atividade atual
            }
        });
    }

    // Método para filtrar e exibir o resumo das tarefas do usuário
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterResumo(String userId) {
        LocalDate dataAtual = LocalDate.now(); // Obter a data atual
        String dataAtualString = dataAtual.toString();
        CollectionReference storiesCollection = FirebaseFirestore.getInstance().collection("tasks"); // Obter a referência da coleção "tasks"
        Query query = storiesCollection.whereEqualTo("auth", userId)
                .whereEqualTo("resumoDate", dataAtualString)
                .whereEqualTo("resumoCount", 1);

        // Executar a consulta no Firestore
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    int count = querySnapshot.size(); // Obter o número de tarefas concluídas hoje
                    inputResumo.setText("Número de tarefas concluídas hoje: " + count); // Exibir o resumo na interface do usuário
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
