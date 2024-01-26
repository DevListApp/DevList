package com.devlist.app.screens.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.repositories.UserRepository;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> userName, userEmail;
    private UserRepository userRepository;

    public ProfileViewModel(){
        userRepository = new UserRepository();
    }
    /*LiveData é uma classe do Android Architecture Components que foi projetada para ser observada por outras partes do seu aplicativo, como componentes de interface do usuário (UI). Essa classe é usada para criar objetos observáveis que podem ser atualizados e notificar automaticamente os observadores sobre mudanças nos dados.*/
    public LiveData<String> getUser() {
        if(userName == null){
            userName = new MutableLiveData<>();
            loadUser();
        }
        return userName;
    }

    public LiveData<String> getEmail() {
        if(userEmail == null){
            userEmail = new MutableLiveData<>();
            loadUser();
        }
        return userEmail;
    }

    public String loadUser(){
        return userRepository.getUser().getName();
    }
    public String loadEmail(){return userRepository.getUser().getEmail();}
}
