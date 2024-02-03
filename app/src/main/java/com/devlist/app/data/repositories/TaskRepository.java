package com.devlist.app.data.repositories;


import androidx.lifecycle.MutableLiveData;

import com.devlist.app.data.models.Task;
import com.devlist.app.data.sources.TaskFirebaseDataSource;

import java.util.Collections;
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

    public MutableLiveData<List<Task>> loadTasks() {
        MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>();

        try {
            taskFirebaseDataSource.getTasks(new TaskFirebaseDataSource.LoadTasksCallback() {
                @Override
                public void onTasksLoaded(List<Task> tasks) {
                    tasksLiveData.setValue(tasks);
                }

                @Override
                public void onDataNotAvailable(Exception exception) {
                    tasksLiveData.setValue(Collections.emptyList());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            tasksLiveData.setValue(Collections.emptyList());
        }

        return tasksLiveData;
    }

    public void updateTask(Task task, TaskFirebaseDataSource.TaskListener listener){
        taskFirebaseDataSource.updateTask(task, new TaskFirebaseDataSource.TaskListener() {
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


    public void deleteTask(String taskId, TaskFirebaseDataSource.TaskListener listener) {
        taskFirebaseDataSource.deleteTask(taskId, new TaskFirebaseDataSource.TaskListener() {
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
