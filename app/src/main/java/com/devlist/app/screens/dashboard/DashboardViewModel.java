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

    private MutableLiveData<String> userName;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<List<Task>> taskList = new MutableLiveData<>();
    private MutableLiveData<Task> selectedTask = new MutableLiveData<>();

    public DashboardViewModel(){
        userRepository = new UserRepository();
        taskRepository = new TaskRepository();
        loadTasksFromFirestore();
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

    public MutableLiveData<List<Task>> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> tasks) {
        taskList.setValue(tasks);
    }

    public void selectTask(Task task) {
        selectedTask.setValue(task);
    }

    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }

    // Método para carregar tarefas do Firestore
    private void loadTasksFromFirestore() {
        taskList = taskRepository.loadTasks();
    }



}
