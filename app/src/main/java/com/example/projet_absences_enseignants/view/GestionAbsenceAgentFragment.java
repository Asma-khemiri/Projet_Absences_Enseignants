package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GestionAbsenceAgentFragment extends Fragment implements AbsenceAdapter.OnItemClickListener, AbsenceAdapter.OnDeleteClickListener {

    private RecyclerView recyclerView;
    private AbsenceAdapter absenceAdapter;
    private List<Absence> absenceList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gestion_absence_agent, container, false);

        // Initialiser la RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialiser la liste des absences
        absenceList = new ArrayList<>();

        // Initialiser l'adaptateur avec l'écouteur de suppression
        absenceAdapter = new AbsenceAdapter(absenceList, this, this);
        recyclerView.setAdapter(absenceAdapter);

        // Appeler la méthode pour récupérer les absences depuis Firebase
        getAbsencesFromFirebase();

        return rootView;
    }

    private void getAbsencesFromFirebase() {
        // Récupérer une instance de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Charger les absences depuis Firestore
        db.collection("absences").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Vider la liste avant de la remplir avec les nouvelles données
                        absenceList.clear();

                        // Parcourir les documents récupérés depuis Firestore
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convertir chaque document en objet Absence
                            Absence absence = document.toObject(Absence.class);
                            absence.setAbsenceID(document.getId());  // Assurez-vous d'avoir un ID pour chaque absence
                            absenceList.add(absence);  // Ajouter l'absence à la liste
                            Log.d("Firestore", "Absence: " + absence.getDate());  // Log pour vérifier les données
                        }

                        if (absenceList.isEmpty()) {
                            Log.d("Firestore", "Aucune donnée récupérée");
                        }

                        // Mettre à jour l'adaptateur après la récupération des données
                        absenceAdapter.notifyDataSetChanged();  // Notifier l'adaptateur que les données ont changé
                    } else {
                        Log.e("Firestore", "Erreur lors de la récupération des données : ", task.getException());
                    }
                });
    }

    // Implémentation de l'interface OnDeleteClickListener pour supprimer l'absence
    @Override
    public void onDeleteClick(Absence absence) {
        // Supprimer l'absence de la liste locale
        absenceList.remove(absence);
        absenceAdapter.notifyDataSetChanged();  // Mettre à jour la RecyclerView

        // Supprimer l'absence de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("absences").document(absence.getAbsenceID())  // Utiliser l'ID de l'absence
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Absence supprimée avec succès");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Erreur lors de la suppression de l'absence : ", e);
                });
    }

    // Implémentation de l'interface OnItemClickListener (si besoin)
    @Override
    public void onItemClick(Absence absence) {
        // Gérer l'événement de clic sur un item, si nécessaire
    }
}
