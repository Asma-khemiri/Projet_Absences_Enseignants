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

    // Sauvegarder l'absence dans Firestore
    public void saveAbsence(String date, String heure, String classe, String enseignement, String statut) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String agentID = user.getUid();
            Absence absence = new Absence(date, heure, classe, enseignement, statut, agentID);

            db.collection("absences")
                    .add(absence)
                    .addOnSuccessListener(documentReference -> {
                        absenceLiveData.setValue(absence);
                        toastMessageLiveData.setValue("Absence enregistrée avec succès");
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                        toastMessageLiveData.setValue("Erreur lors de l'enregistrement de l'absence");
                    });
        } else {
            absenceLiveData.setValue(null);
            toastMessageLiveData.setValue("Utilisateur non connecté");
        }
    }
}
