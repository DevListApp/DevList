package com.devlist.app.screens.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.repositories.UserRepository;
import com.devlist.app.data.sources.UserFirebaseDataSource;

import java.util.List;

public class ViewModelUser extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<List<String>> user = new MutableLiveData<>();


    public ViewModelUser() {
        userRepository = new UserRepository();
    }

    public void creatUser(List<String> user, UserFirebaseDataSource.UserCreationListener listener){
        userRepository.createUser(user, new UserFirebaseDataSource.UserCreationListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }
            @Override
            public void onFailure(String errorMessage) {
                listener.onFailure(errorMessage);
            }
        });
    }
}
