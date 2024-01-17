package com.devlist.app.data.repositories;

import com.devlist.app.data.models.User;
import com.devlist.app.data.sources.UserFirebaseDataSource;

import java.util.List;

public class UserRepository {

    private UserFirebaseDataSource userFirebaseDataSource;

    public UserRepository() {
        userFirebaseDataSource = new UserFirebaseDataSource();
    }

    public List<User> getAllUsers() {
        // Lógica para buscar todos os usuários do Firebase
        return null;
    }

    public User getUserByUid(String userUid) {
        // Lógica para buscar uma tarefa específica do Firebase pelo ID
        return null;
    }

    public User getUser(){
        User user = new User(userFirebaseDataSource.getUserAuthenticator());
        return user;
    }


    public void createUser(List<String> user, UserFirebaseDataSource.UserCreationListener listener) {
        userFirebaseDataSource.createUserAuth(user, new UserFirebaseDataSource.UserCreationListener() {
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

    public void updateUser(User user) {
        // Lógica para atualizar uma tarefa existente no Firebase
    }

    public void deleteUser(String userUid) {
        // Lógica para excluir uma tarefa do Firebase pelo ID
    }
}
