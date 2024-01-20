package com.devlist.app.data.repositories;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.devlist.app.data.models.Task;
import com.devlist.app.data.sources.TaskFirebaseDataSource;

import java.util.List;

public class TaskRepository {
    private TaskFirebaseDataSource taskFirebaseDataSource;
    public TaskRepository(){
        taskFirebaseDataSource = new TaskFirebaseDataSource();
    }
    public List<Task> getAllTasks() {
        // Lógica para buscar todas as tarefas do Firebase
        return null;
    }

    public Task getTaskById(String taskId) {
        // Lógica para buscar uma tarefa específica do Firebase pelo ID
        return null;
    }

    public void createTask(Task task, TaskFirebaseDataSource.TaskListener listener) {
        taskFirebaseDataSource.createTask(task, new TaskFirebaseDataSource.TaskListener() {
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

    public void setFinishTask(Task task, TaskFirebaseDataSource.TaskListener listener) {
        taskFirebaseDataSource.setFinishTask(task, new TaskFirebaseDataSource.TaskListener() {
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

    public void deleteTask(String taskId) {
        // Lógica para excluir uma tarefa do Firebase pelo ID
    }

}
