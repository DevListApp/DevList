package com.devlist.app.screens.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.repositories.UserRepository;
import com.devlist.app.data.sources.UserFirebaseDataSource;

import java.util.List;

public class ViewModelUser extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<List<String>> user = new MutableLiveData<>();

    // Construtor da ViewModelUser
    public ViewModelUser() {
        userRepository = new UserRepository();
    }

    // Método para criar um usuário
    public void createUser(List<String> user, UserFirebaseDataSource.UserCreationListener listener){
        // Chamando o método do repositório para criar um usuário
        userRepository.createUser(user, new UserFirebaseDataSource.UserCreationListener() {
            @Override
            public void onSuccess() {
                // Chamando o método de sucesso do ouvinte passado como parâmetro
                listener.onSuccess();
            }
            @Override
            public void onFailure(String errorMessage) {
                // Chamando o método de falha do ouvinte passado como parâmetro
                listener.onFailure(errorMessage);
            }
        });
    }
}
