package com.devlist.app.screens.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.models.User;
import com.devlist.app.data.repositories.UserRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<String> userName = new MutableLiveData<>();

    public HomeViewModel(){
        userRepository = new UserRepository();
    }

    public LiveData<String> getUserName(MutableLiveData<String> name){
        if (userName == null) getNameUser();
        userName = name;
        return userName;
    }

    public void loadUserData() {
        User user = userRepository.getUser();
    }

    public String getNameUser(){
        return userRepository.getUser().getName();
    }

}
