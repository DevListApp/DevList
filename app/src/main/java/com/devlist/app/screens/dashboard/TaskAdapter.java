package com.devlist.app.screens.dashboard;

import static android.view.View.resolveSizeAndState;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
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
import com.devlist.app.screens.task.UpdateTask;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList; // Lista de tarefas a ser exibida
    private DashboardViewModel viewModel; // ViewModel associado ao adaptador
    private TaskRepository taskRepository; // Repositório para manipulação de tarefas

    // Construtor do adaptador
    public TaskAdapter(List<Task> taskList, DashboardViewModel viewModel) {
        this.taskList = taskList;
        this.viewModel = viewModel;
        taskRepository = new TaskRepository();
    }

    // ViewHolder mantém a referência aos elementos de uma única linha
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView; // TextView para o título da tarefa
        private final TextView dt_scheduled; // TextView para a data de agendamento
        private ImageView statusTask; // ImageView para o status da tarefa
        private CheckBox finishTask; // CheckBox para marcar a tarefa como concluída
        private Context context;

        // Construtor do ViewHolder
        public TaskViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            // Inicialização dos elementos de UI
            titleTextView = view.findViewById(R.id.text_list);
            dt_scheduled = view.findViewById(R.id.dt_scheduled);
            statusTask = view.findViewById(R.id.statusTask);
            finishTask = view.findViewById(R.id.checkTask);
        }

        // Método para vincular os dados da tarefa aos elementos de UI
        public void bind(Task task) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String dataFinal = dateFormat.format(task.getHoraFim());
            titleTextView.setText(task.getTitulo()); // Define o título da tarefa
            dt_scheduled.setText(dataFinal); // Define a data de agendamento da tarefa
            if(task.getConcluido() > 0) finishTask.setChecked(true); // Define o estado do CheckBox de conclusão
            setStatusTask(task); // Define o status da tarefa com base na data de conclusão
        }

        // Método para definir o status da tarefa com base na data de conclusão
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

    // Método chamado quando RecyclerView precisa de um novo ViewHolder
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item de tarefa
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new TaskViewHolder(view, parent.getContext());
    }

    // Método chamado para vincular os dados da tarefa a um ViewHolder específico
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position); // Obtém a tarefa na posição atual
        holder.finishTask.setChecked(task.getConcluido() > 0); // Define o estado do CheckBox de conclusão
        holder.bind(task); // Vincula os dados da tarefa aos elementos de UI no ViewHolder

        LocalDate data = LocalDate.now();
        String dataFinalizacao = data.toString();

        // Define o OnClickListener para o CheckBox de conclusão
        holder.finishTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = task.getConcluido() > 0 ? 0 : 1; // Alterna o status de conclusão da tarefa
                task.setConcluido(status); // Define o novo status de conclusão da tarefa
                if(task.getResumoCount() != 0 ){
                    task.setResumoCount(0);
                    task.setResumoDate("");
                }else{
                    task.setResumoCount(1);
                    task.setResumoDate(dataFinalizacao);
                }

                // Atualiza o status da tarefa no banco de dados
                taskRepository.setFinishTask(task, new TaskFirebaseDataSource.TaskListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(v.getContext(), "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        holder.finishTask.setChecked(task.getConcluido() > 0); // Atualiza o estado do CheckBox de conclusão
                        notifyDataSetChanged(); // Notifica o adaptador sobre as mudanças nos dados
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(v.getContext(), errorMessage, Toast.LENGTH_SHORT).show(); // Exibe uma mensagem de erro
                    }
                });
            }
        });

        // Define o OnClickListener para o item de tarefa
        holder.itemView.setOnClickListener(view -> {
            viewModel.selectTask(task); // Seleciona a tarefa para edição
        });
    }

    // Método para definir uma nova lista de tarefas
    public void setTasks(List<Task> tasks) {
        taskList = tasks; // Define a nova lista de tarefas
        notifyDataSetChanged(); // Notifica o adaptador sobre as mudanças nos dados
    }

    // Método para atualizar a lista de tarefas
    public void updateTaskList(List<Task> newTaskList) {
        taskList.clear(); // Limpa a lista atual de tarefas
        taskList.addAll(newTaskList); // Adiciona todas as novas tarefas à lista
        notifyDataSetChanged(); // Notifica o adaptador sobre as mudanças nos dados
    }

    // Método para obter o número total de itens na lista de tarefas
    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
