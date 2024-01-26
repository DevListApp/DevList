package com.devlist.app.screens.dashboard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.devlist.app.R;
import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.TaskRepository;
import com.devlist.app.data.sources.TaskFirebaseDataSource;
import com.devlist.app.screens.task.UpdateTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private TaskRepository taskRepository;

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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateTask.class);
                    context.startActivity(intent);
                    // Finalizar a Activity atual
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
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

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new TaskViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        taskRepository = new TaskRepository();
        Task task = taskList.get(position);
        holder.finishTask.setChecked( task.getConcluido() > 0 );
        holder.bind(task);
        holder.finishTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = task.getConcluido()  > 0 ? 0 : 1;
                task.setConcluido(status);
                taskRepository.setFinishTask(task, new TaskFirebaseDataSource.TaskListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(v.getContext() , "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show(); // ALERT
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(v.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
                holder.finishTask.setChecked(task.getConcluido() > 0);
                Log.i(TAG, "Meu objeto => " + task.getConcluido());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

}

