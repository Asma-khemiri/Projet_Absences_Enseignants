package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.projet_absences_enseignants.model.Absence;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

public class AddAbsenceViewModel extends AndroidViewModel {
    private MutableLiveData<Absence> absenceLiveData;
    private MutableLiveData<String> toastMessageLiveData;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public AddAbsenceViewModel(Application application) {
        super(application);
        absenceLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance(); // Initialiser Firebase Firestore
        mAuth = FirebaseAuth.getInstance();  // Initialiser Firebase Authentication
    }

    public MutableLiveData<Absence> getAbsenceLiveData() {
        return absenceLiveData;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }

    // Cette méthode va être appelée pour sauvegarder l'absence dans Firestore
    public void saveAbsence(String date, String heure, String classe, String enseignement) {
        // Vérifier si l'utilisateur est connecté
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Utiliser l'UID de l'utilisateur connecté comme identifiant d'agent
            String agentID = user.getUid();

            Absence absence = new Absence(date, heure, classe, enseignement, agentID);

            db.collection("absences")
                    .add(absence)
                    .addOnSuccessListener(documentReference -> {
                        absenceLiveData.setValue(absence); // Mettre à jour la LiveData avec l'absence ajoutée
                        toastMessageLiveData.setValue("Absence enregistrée avec succès");
                    })
                    .addOnFailureListener(e -> {
                        // Gérer l'erreur si la sauvegarde échoue
                        e.printStackTrace();
                        toastMessageLiveData.setValue("Erreur lors de l'enregistrement de l'absence");
                    });
        } else {
            // L'utilisateur n'est pas connecté, gérer cette situation
            absenceLiveData.setValue(null);
            toastMessageLiveData.setValue("Utilisateur non connecté");
        }
    }
}
