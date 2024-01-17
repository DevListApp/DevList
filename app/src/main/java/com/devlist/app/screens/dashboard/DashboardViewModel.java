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
