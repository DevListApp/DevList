package com.devlist.app.screens.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devlist.app.R;
import com.devlist.app.data.models.Task;

import java.text.DateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    //Um ViewHolder mantém a referência aos elementos de uma única linha
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView dt_scheduled;

        public TaskViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.text_list);
            dt_scheduled = view.findViewById(R.id.dt_scheduled);
        }

        public void bind(Task task) {
            titleTextView.setText(task.getTitulo());
            dt_scheduled.setText("17 nov , 2023");
        }
    }

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }


}

