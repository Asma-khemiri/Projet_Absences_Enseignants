package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projet_absences_enseignants.model.Absence;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AbsenceViewModel extends AndroidViewModel {
    private MutableLiveData<List<Absence>> absencesLiveData;
    private MutableLiveData<String> toastMessageLiveData;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public AbsenceViewModel(Application application) {
        super(application);
        absencesLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<List<Absence>> getAbsencesLiveData() {
        return absencesLiveData;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }

    // Récupérer les absences d'un enseignant connecté pour un enseignement spécifique
    public void loadAbsencesForEnseignant(String enseignement) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String enseignantID = user.getUid();  // Récupérer l'ID de l'enseignant connecté

            // Requête pour récupérer toutes les absences de cet enseignant pour l'enseignement spécifique
            db.collection("absences")
                    .whereEqualTo("enseignantID", enseignantID)  // Filtrer par l'ID de l'enseignant
                    .whereEqualTo("enseignement", enseignement)  // Filtrer par l'enseignement
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<Absence> absences = queryDocumentSnapshots.toObjects(Absence.class);
                            absencesLiveData.setValue(absences);  // Mettre à jour la liste des absences
                            toastMessageLiveData.setValue("Absences récupérées avec succès");
                        } else {
                            toastMessageLiveData.setValue("Aucune absence trouvée pour cet enseignement");
                        }
                    })
                    .addOnFailureListener(e -> {
                        toastMessageLiveData.setValue("Erreur lors de la récupération des absences");
                    });
        } else {
            toastMessageLiveData.setValue("Utilisateur non connecté");
        }
    }
}
