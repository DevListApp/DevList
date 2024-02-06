package com.devlist.app.screens.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.TaskRepository;
import com.devlist.app.data.repositories.UserRepository;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    // LiveData para armazenar o nome do usuário
    private MutableLiveData<String> userName;

    // Repositórios e Firestore
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private FirebaseFirestore firebaseFirestore;

    // LiveData para armazenar a lista de tarefas e a tarefa selecionada
    private MutableLiveData<List<Task>> taskList = new MutableLiveData<>();
    private MutableLiveData<Task> selectedTask = new MutableLiveData<>();

    // Construtor
    public DashboardViewModel(){
        userRepository = new UserRepository();
        taskRepository = new TaskRepository();
        loadTasksFromFirestore(); // Carrega as tarefas do Firestore ao criar o ViewModel
    }

    // Método para obter o LiveData do nome do usuário
    public LiveData<String> getUser() {
        if(userName == null){
            userName = new MutableLiveData<>();
            loadUser(); // Carrega o nome do usuário ao inicializar o LiveData
        }
        return userName;
    }

    // Método para carregar o nome do usuário
    public String loadUser(){
        return userRepository.getUser().getName();
    }

    // Getter e setter para a lista de tarefas
    public MutableLiveData<List<Task>> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> tasks) {
        taskList.setValue(tasks);
    }

    // Método para selecionar uma tarefa
    public void selectTask(Task task) {
        selectedTask.setValue(task);
    }

    // Getter para obter a tarefa selecionada
    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }

    // Método para carregar as tarefas do Firestore
    private void loadTasksFromFirestore() {
        taskList = taskRepository.loadTasks();
    }
}
