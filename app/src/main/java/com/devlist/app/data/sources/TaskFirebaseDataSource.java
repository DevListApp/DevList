package com.devlist.app.data.sources;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.devlist.app.data.models.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TaskFirebaseDataSource {
    private FirebaseFirestore databaseReference;
    private DocumentReference documentReference;

    public TaskFirebaseDataSource() {
        databaseReference = FirebaseFirestore.getInstance();
    }

    public interface TaskListener {
        void onSuccess();
        void onFailure(String errorMessage);
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

}
