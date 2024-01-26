package com.devlist.app.screens.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.models.User;
import com.devlist.app.data.repositories.UserRepository;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> userName;
    private UserRepository userRepository;

    public DashboardViewModel(){
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

    public String loadUser(){
       return userRepository.getUser().getName();
    }


}
