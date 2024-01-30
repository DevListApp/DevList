package com.devlist.app.data.sources;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devlist.app.data.models.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TaskFirebaseDataSource {
    private FirebaseFirestore databaseReference;
    private DocumentReference documentReference;
    FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth authUser = FirebaseAuth.getInstance();

    public TaskFirebaseDataSource() {
        databaseReference = FirebaseFirestore.getInstance();
    }

    public FirebaseUser getUserAuthenticator(){
        return authUser.getCurrentUser();
    }

    public interface TaskListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface LoadTasksCallback {
        void onTasksLoaded(List<Task> tasks);
        void onDataNotAvailable(Exception exception);
    }
    public void createTask(Task tarefa , TaskListener listener){
        // AO CLICAR, ENVIA OS DADOS PARA PODER SALVAR NO BANCO
        databaseReference.collection("tasks").add(tarefa)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Map<String, Object> id = new HashMap<>();
                    id.put("id", documentReference.getId());
                    documentReference.update(id);
                    listener.onSuccess();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onFailure("Erro ao adicionar a tarefa: " + e.getMessage());
                }
            });
    }

    public void setFinishTask(Task tarefa, TaskListener listener){
        documentReference = databaseReference.collection("tasks").document(tarefa.getId());
        Map<String, Object> updates = new HashMap<>();
        updates.put("concluido", tarefa.getConcluido());
        updates.put("resumoDate", tarefa.getResumoDate());
        updates.put("resumoCount", tarefa.getResumoCount());

        documentReference
                .update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            listener.onSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailure("Erro ao atualizar tarefa! " + e.getMessage());
                        }
                    });
    }

    public void getTasks(LoadTasksCallback callback) {
        FirebaseUser user = getUserAuthenticator();
        List<Task> loadedTasks = new ArrayList<>();
        String userId = user.getUid();

        // Obter dados da coleção "tasks"
        CollectionReference tasksCollection = databaseReference.collection("tasks");
        // Query de consulta no banco de dados
        Query query = tasksCollection.whereEqualTo("auth", userId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("TaskFirebaseDataSource", "Loading tasks from Firestore ^_^!!!");
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Task model = document.toObject(Task.class);
                    loadedTasks.add(model);
                }
                callback.onTasksLoaded(loadedTasks);
            } else {
                Log.e("TaskFirebaseDataSource", "Error loading tasks from Firestore", task.getException());
                callback.onDataNotAvailable(task.getException());
            }
        });
    }

    public void updateTask(Task task, TaskListener listener){
        documentReference = databaseReference.collection("tasks").document(task.getId());
        documentReference
                .update(getMapTask(task))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure("Erro ao atualizar está tarefa!" + e.getMessage());
                    }
                });

    }

    public Map<String, Object> getMapTask(Task task){
        Map<String, Object> taskUpdate = new HashMap<>();
        taskUpdate.put("auth", task.getAuth());
        taskUpdate.put("concluido", task.getConcluido());
        taskUpdate.put("horaFim", task.getHoraFim());
        taskUpdate.put("horaInicio", task.getHoraInicio());
        taskUpdate.put("id", task.getId());
        taskUpdate.put("notas", task.getNotas());
        taskUpdate.put("prioridade", task.getPrioridade());
        taskUpdate.put("titulo", task.getTitulo());
        return taskUpdate;
    }

    public void deleteTask(String TaskId, TaskListener listener){
        documentReference = databaseReference.collection("tasks").document(TaskId);
        documentReference
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure("Erro ao excluir esta tarefa " + e.getMessage());
                    }
                });
    }

}
