package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.viewmodel.AddAbsenceViewModel;
import com.example.projet_absences_enseignants.R;

public class AddAbsenceFragment extends Fragment {

    private AddAbsenceViewModel viewModel;

    // Déclarations des vues
    private EditText etDate, etHeure, etClasse, etEnseignement;
    private Button btnSaveAbsence;
    private TextView tvAddAbsenceTitle;

    public AddAbsenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_absence, container, false);

        // Initialiser les vues
        etDate = view.findViewById(R.id.etDate);
        etHeure = view.findViewById(R.id.etHeure);
        etClasse = view.findViewById(R.id.etClasse);
        etEnseignement = view.findViewById(R.id.etEnseignement);
        btnSaveAbsence = view.findViewById(R.id.btnSaveAbsence);
        tvAddAbsenceTitle = view.findViewById(R.id.tvAddAbsenceTitle);

        // Initialiser ViewModel
        viewModel = new ViewModelProvider(this).get(AddAbsenceViewModel.class);

        // Observer des changements dans le message Toast
        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), message -> {
            // Affichez le message Toast
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        // Bouton d'enregistrement
        btnSaveAbsence.setOnClickListener(v -> {
            // Récupérer les données saisies par l'utilisateur
            String date = etDate.getText().toString();
            String heure = etHeure.getText().toString();
            String classe = etClasse.getText().toString();
            String enseignement = etEnseignement.getText().toString();

            // Vérification que tous les champs sont remplis
            if (date.isEmpty() || heure.isEmpty() || classe.isEmpty() || enseignement.isEmpty()) {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Appel à la méthode du ViewModel pour sauvegarder l'absence
            viewModel.saveAbsence(date, heure, classe, enseignement);
        });

        return view;
    }
}
