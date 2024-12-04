package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.AddAbsenceViewModel;

public class AddAbsenceFragment extends Fragment {
    private AddAbsenceViewModel addAbsenceViewModel;
    private Spinner spinnerJustification;
    private EditText etDate, etHeure, etClasse, etEnseignement;
    private Button btnSaveAbsence;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addAbsenceViewModel = new ViewModelProvider(this).get(AddAbsenceViewModel.class);

        View root = inflater.inflate(R.layout.fragment_add_absence, container, false);

        etDate = root.findViewById(R.id.etDate);
        etHeure = root.findViewById(R.id.etHeure);
        etClasse = root.findViewById(R.id.etClasse);
        etEnseignement = root.findViewById(R.id.etEnseignement);
        spinnerJustification = root.findViewById(R.id.spinnerJustification);

        // Initialiser le Spinner pour la justification
        ArrayAdapter<CharSequence> adapterJustification = ArrayAdapter.createFromResource(
                getContext(),
                R.array.justification_array, // La liste des justifications définie dans strings.xml
                android.R.layout.simple_spinner_item
        );
        adapterJustification.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJustification.setAdapter(adapterJustification);

        btnSaveAbsence = root.findViewById(R.id.btnSaveAbsence);
        btnSaveAbsence.setOnClickListener(v -> {
            // Récupérer les valeurs des champs
            String date = etDate.getText().toString();
            String heure = etHeure.getText().toString();
            String classe = etClasse.getText().toString();
            String enseignement = etEnseignement.getText().toString();
            String justification = spinnerJustification.getSelectedItem().toString();

            // Vérifier que les champs ne sont pas vides
            if (date.isEmpty() || heure.isEmpty() || classe.isEmpty() || enseignement.isEmpty()) {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sauvegarder l'absence via le ViewModel
            addAbsenceViewModel.saveAbsence(date, heure, classe, enseignement, justification);
        });

        addAbsenceViewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}
