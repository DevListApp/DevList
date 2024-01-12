package com.devlist.app.data.repositories;

import com.devlist.app.data.models.User;

import java.util.List;

public class UserRepository {
    public List<User> getAllUsers() {
        // Lógica para buscar todos os usuários do Firebase
        return null;
    }

    public User getUserByUid(String userUid) {
        // Lógica para buscar uma tarefa específica do Firebase pelo ID
        return null;
    }

    public void createUser(User user) {
        // Lógica para criar uma nova tarefa no Firebase
    }

    public void updateUser(User user) {
        // Lógica para atualizar uma tarefa existente no Firebase
    }

    public void deleteUser(String userUid) {
        // Lógica para excluir uma tarefa do Firebase pelo ID
    }
}
