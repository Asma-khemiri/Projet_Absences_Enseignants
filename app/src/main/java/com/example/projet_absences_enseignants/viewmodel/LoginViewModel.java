package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MutableLiveData<Boolean> isLoginSuccessful;
    private MutableLiveData<String> userRole;
    private MutableLiveData<String> errorMessage; // Nouveau champ pour gérer les erreurs

    public LoginViewModel(Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        isLoginSuccessful = new MutableLiveData<>();
        userRole = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<Boolean> getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

    public LiveData<String> getUserRole() {
        return userRole;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage; // Retourne l'erreur
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            checkUserRole(user.getUid());
                        }
                    } else {
                        errorMessage.setValue("Erreur de connexion : " + task.getException().getMessage());
                        isLoginSuccessful.setValue(false);
                    }
                });
    }

    private void checkUserRole(String userId) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        userRole.setValue(role); // Passer le rôle à la vue
                        isLoginSuccessful.setValue(true); // Connexion réussie
                    } else {
                        errorMessage.setValue("Rôle utilisateur non trouvé.");
                        isLoginSuccessful.setValue(false);
                    }
                })
                .addOnFailureListener(e -> {
                    errorMessage.setValue("Erreur lors de la récupération du rôle : " + e.getMessage());
                    isLoginSuccessful.setValue(false);
                });
    }
}
