package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.projet_absences_enseignants.model.Absence;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GestionAbsenceViewModel extends AndroidViewModel {

    private MutableLiveData<List<Absence>> absencesLiveData;
    private FirebaseFirestore db;

    public GestionAbsenceViewModel(Application application) {
        super(application);
        absencesLiveData = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<Absence>> getAbsencesLiveData() {
        return absencesLiveData;
    }

    // Charger toutes les absences depuis Firestore
    public void loadAbsences() {
        db.collection("absences")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Absence> absences = queryDocumentSnapshots.toObjects(Absence.class);
                    absencesLiveData.setValue(absences);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }
}
