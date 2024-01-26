package com.devlist.app.screens.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.devlist.app.R;
import com.devlist.app.data.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Profile extends AppCompatActivity {
    TextView userName, inputEmail;

    Button btn_sair;

    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.userName);
        inputEmail = findViewById(R.id.inputEmail);
        btn_sair = findViewById(R.id.btn_sair);
        ProfileViewModel viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        userRepository = new UserRepository();

        userName.setText(viewModel.loadUser());
        inputEmail.setText(viewModel.loadEmail());

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.logoutUser();
            }
        });
    }


}