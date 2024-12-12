package com.example.projet_absences_enseignants.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;
import com.example.projet_absences_enseignants.viewmodel.AbsenceViewModel;
import com.example.projet_absences_enseignants.viewmodel.GestionAbsenceViewModel;

public class AbsenceListFragment extends Fragment implements AbsenceAdapter.OnItemClickListener, AbsenceAdapter.OnDeleteClickListener, AbsenceAdapter.OnModifyClickListener {

    private RecyclerView recyclerView;
    private AbsenceAdapter absenceAdapter;
    private AbsenceViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gestion_absence, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialisation du ViewModel
        viewModel = new ViewModelProvider(this).get(GestionAbsenceViewModel.class);

        // Initialisation de l'adaptateur avec le listener de modification
        absenceAdapter = new AbsenceAdapter(viewModel.getAbsenceList(), this, this, this);
        recyclerView.setAdapter(absenceAdapter);

        // Observer les données pour mettre à jour l'UI lorsque la liste des absences change
        viewModel.getAbsences().observe(getViewLifecycleOwner(), absences -> {
            absenceAdapter.notifyDataSetChanged();
        });

        // Charger les absences depuis Firebase
        viewModel.getAbsencesFromFirebase();

        return rootView;
    }

    @Override
    public void onDeleteClick(Absence absence) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Confirmation")
                    .setMessage("Êtes-vous sûr de vouloir supprimer cette absence ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        // Passer le contexte à la méthode de suppression
                        viewModel.deleteAbsence(absence, getContext());
                    })
                    .setNegativeButton("Non", null)
                    .show();
        }
    }

    @Override
    public void onItemClick(Absence absence) {
        Toast.makeText(getContext(), "Absence: " + absence.getEnseignement(), Toast.LENGTH_SHORT).show();
    }
}
