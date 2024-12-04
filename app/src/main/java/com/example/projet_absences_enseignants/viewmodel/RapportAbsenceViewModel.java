package com.example.projet_absences_enseignants.viewmodel;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.example.projet_absences_enseignants.model.Absence;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RapportAbsenceViewModel extends ViewModel {

    private MutableLiveData<List<Absence>> absencesLiveData;

    public RapportAbsenceViewModel() {
        absencesLiveData = new MutableLiveData<>();
        loadAbsences();
    }

    public LiveData<List<Absence>> getAbsences() {
        return absencesLiveData;
    }

    // Récupérer les données d'absences de Firestore en temps réel
    private void loadAbsences() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("absences")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("AbsenceViewModel", "Error fetching data.", error);
                            return;
                        }

                        if (value != null && !value.isEmpty()) {
                            List<Absence> absences = new ArrayList<>();
                            for (DocumentSnapshot document : value.getDocuments()) {
                                Absence absence = document.toObject(Absence.class);
                                if (absence != null) {
                                    absences.add(absence);
                                }
                            }
                            absencesLiveData.setValue(absences);  // Mettre à jour les données en LiveData
                        } else {
                            Log.d("AbsenceViewModel", "No data available.");
                        }
                    }
                });
    }

}
