package com.devlist.app.screens.profile;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlist.app.data.models.Task;
import com.devlist.app.data.repositories.UserRepository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> userName, userEmail, userId;
    private UserRepository userRepository;

    public ProfileViewModel(){
        userRepository = new UserRepository();
    }
    private DocumentReference firebaseFirestore;
    /*LiveData é uma classe do Android Architecture Components que foi projetada para ser observada por outras partes do seu aplicativo, como componentes de interface do usuário (UI). Essa classe é usada para criar objetos observáveis que podem ser atualizados e notificar automaticamente os observadores sobre mudanças nos dados.*/
    public LiveData<String> getUser() {
        if(userName == null){
            userName = new MutableLiveData<>();
            loadUser();
        }
        return userName;
    }

    public LiveData<String> getEmail() {
        if(userEmail == null){
            userEmail = new MutableLiveData<>();
            loadUser();
        }
        return userEmail;
    }

    public LiveData<String> getId() {
        if(userId == null){
            userId = new MutableLiveData<>();
            loadId();
        }
        return userId;
    }

//    public void filterResumo(OnCountRetrievedListener listener) {
//        CollectionReference storiesCollection = FirebaseFirestore.getInstance().collection("tasks");
//        Query query = storiesCollection.whereEqualTo("auth", userId)
//                .whereEqualTo("resumoDate", dataAtualString);
//
//        // Executa a consulta
//        query.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                QuerySnapshot querySnapshot = task.getResult();
//                if (querySnapshot != null) {
//                    int count = querySnapshot.size();
//                    System.out.println("Número de documentos retornados: " + count);
//                    listener.onCountRetrieved(count);
//                } else {
//                    System.out.println("Nenhum documento correspondente encontrado.");
//                }
//            } else {
//                Exception exception = task.getException();
//                if (exception != null) {
//                    System.out.println("Erro ao executar a consulta: " + exception.getMessage());
//                }
//            }
//        });
//    }


    public String loadUser(){
        return userRepository.getUser().getName();
    }
    public String loadEmail(){return userRepository.getUser().getEmail();}
    public String loadId(){return userRepository.getUser().getId();}
}
