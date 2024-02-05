package com.devlist.app.screens.dashboard;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.devlist.app.R;
import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.TaskRepository;
import com.devlist.app.data.sources.TaskFirebaseDataSource;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private DashboardViewModel viewModel;
    private TaskRepository taskRepository;
    // Construtor do adaptador
    public TaskAdapter(List<Task> taskList, DashboardViewModel viewModel) {
        this.taskList = taskList;
        this.viewModel = viewModel;
        taskRepository = new TaskRepository();
    }
    //Um ViewHolder mantém a referência aos elementos de uma única linha
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView dt_scheduled;
        private ImageView statusTask;
        private CheckBox finishTask;
        private Context context;


        public TaskViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            titleTextView = view.findViewById(R.id.text_list);
            dt_scheduled = view.findViewById(R.id.dt_scheduled);
            statusTask = view.findViewById(R.id.statusTask);
            finishTask = view.findViewById(R.id.checkTask);

        }

        public void bind(Task task) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String dataFinal = dateFormat.format(task.getHoraFim());
            titleTextView.setText(task.getTitulo());
            dt_scheduled.setText(dataFinal);
            if(task.getConcluido() > 0) finishTask.setChecked(true);
            setStatusTask(task);
        }
        public void setStatusTask(Task task){
            if (task.getHoraFim().before(new Date()) && task.getConcluido() != 1) {
                statusTask.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
            } else if(task.getHoraFim().after(new Date()) && task.getConcluido() != 1){
                statusTask.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
            } else {
                statusTask.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            }
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new TaskViewHolder(view, parent.getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.finishTask.setChecked(task.getConcluido() > 0);
        holder.bind(task);
        LocalDate data = LocalDate.now();
        String dataFinalizacao = data.toString();

        holder.finishTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = task.getConcluido() > 0 ? 0 : 1;
                task.setConcluido(status);

                if(task.getResumoCount() != 0 ){
                    task.setResumoCount(0);
                    task.setResumoDate("");
                }else{
                    task.setResumoCount(1);
                    task.setResumoDate(dataFinalizacao);
                }

                taskRepository.setFinishTask(task, new TaskFirebaseDataSource.TaskListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(v.getContext(), "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        // Atualize o estado do 'finishTask' após o sucesso da operação
                        holder.finishTask.setChecked(task.getConcluido() > 0);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(v.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(view -> {
            viewModel.selectTask(task);
        });
    }

    public void setTasks(List<Task> tasks) {
        taskList = tasks;
        notifyDataSetChanged();
    }

    public void updateTaskList(List<Task> newTaskList) {
        taskList.clear();
        taskList.addAll(newTaskList);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

}

