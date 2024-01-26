package com.devlist.app.screens.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.TaskRepository;

import java.util.List;

public class ViewModelUpdateTask extends ViewModel {
    private TaskRepository taskRepository;

    private MutableLiveData<Task> task = new MutableLiveData<>();

    public ViewModelUpdateTask(){
        taskRepository = new TaskRepository();
    }

    public LiveData<Task> getTask() {
        return task;
    }










}
