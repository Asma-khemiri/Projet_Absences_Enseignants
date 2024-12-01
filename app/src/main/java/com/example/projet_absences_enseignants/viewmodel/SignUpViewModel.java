package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projet_absences_enseignants.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MutableLiveData<String> successMessage;
    private MutableLiveData<String> errorMessage;

    public SignUpViewModel(Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        successMessage = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void createUser(String name, String email, String password, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        User user = new User(name, email, role);
                        db.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    successMessage.setValue("Inscription réussie");
                                })
                                .addOnFailureListener(e -> {
                                    errorMessage.setValue("Erreur lors de l'ajout du rôle utilisateur.");
                                });
                    } else {
                        String errorMsg = task.getException() != null ? task.getException().getMessage() : "Échec de l'inscription";
                        errorMessage.setValue(errorMsg); // Affiche un message d'erreur plus détaillé
                    }
                });
    }
}
