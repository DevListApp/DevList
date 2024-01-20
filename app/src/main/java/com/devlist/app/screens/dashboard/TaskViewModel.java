package com.devlist.app.screens.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.TaskRepository;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private TaskRepository taskRepository;

    // LiveData para observar a lista de tarefas
    private MutableLiveData<List<Task>> taskListLiveData = new MutableLiveData<>();

    public TaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        // Inicializa a lista de tarefas ao criar o ViewModel
        loadTasks();
    }

    // Método para obter a lista de tarefas como LiveData
    public LiveData<List<Task>> getTaskListLiveData() {
        return taskListLiveData;
    }

    // Método para carregar as tarefas do repositório
    private void loadTasks() {
        List<Task> tasks = taskRepository.getAllTasks();
        taskListLiveData.setValue(tasks);
    }

    // Método para atualizar o título de uma tarefa
    /*public void updateTaskTitle(Task task, String newTitle) {
        task.setTitulo(newTitle);
        taskRepository.updateTask(task);

        // Atualiza a lista de tarefas após a modificação
        loadTasks();
    }*/
}
