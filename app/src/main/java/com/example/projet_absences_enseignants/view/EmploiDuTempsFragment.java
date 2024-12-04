package com.example.projet_absences_enseignants.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.EmploiDuTemps;
import com.example.projet_absences_enseignants.viewmodel.EmploiDuTempsViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmploiDuTempsFragment extends Fragment {

    private EmploiDuTempsViewModel emploiDuTempsViewModel;
    private TableLayout tableLayout;
    private ActivityResultLauncher<String> getContentLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emploi_du_temps, container, false);

        emploiDuTempsViewModel = new ViewModelProvider(this).get(EmploiDuTempsViewModel.class);

        tableLayout = view.findViewById(R.id.tableLayout);

        emploiDuTempsViewModel.getEmploiDuTempsList().observe(getViewLifecycleOwner(), emplois -> {
            if (emplois != null && !emplois.isEmpty()) {
                tableLayout.removeAllViews();

                for (EmploiDuTemps emploi : emplois) {
                    addRowToTable(emploi);
                }
            }
        });

        Button buttonUpload = view.findViewById(R.id.button_upload);
        buttonUpload.setOnClickListener(v -> selectExcelFile());

        // Enregistrement du contrat pour la sélection de fichier
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                // Traitez le fichier en arrière-plan pour éviter le blocage
                processFileInBackground(result);
            } else {
                Toast.makeText(getContext(), "Erreur lors de la récupération du fichier", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Méthode pour sélectionner un fichier Excel
    private void selectExcelFile() {
        try {
            // Lancer la sélection du fichier Excel
            getContentLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour traiter le fichier Excel en arrière-plan
    private void processFileInBackground(Uri fileUri) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Traitez le fichier ici, par exemple en lisant les données du fichier Excel
                emploiDuTempsViewModel.loadEmploisDuTempsFromFile(fileUri, getContext());

                // Vous pouvez aussi mettre à jour l'UI après le traitement du fichier
                getActivity().runOnUiThread(() -> {
                    // Mise à jour UI sur le thread principal
                    Toast.makeText(getContext(), "Fichier traité avec succès", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Erreur lors du traitement du fichier", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Méthode pour ajouter une ligne dans le TableLayout
    private void addRowToTable(EmploiDuTemps emploi) {
        // Créer une nouvelle ligne de table
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        // Ajouter chaque champ de l'emploi du temps comme un TextView dans la ligne
        tableRow.addView(createTextView(emploi.getJour()));
        tableRow.addView(createTextView(emploi.getMatiere()));
        tableRow.addView(createTextView(emploi.getHeureDebut()));
        tableRow.addView(createTextView(emploi.getHeureFin()));

        // Ajouter la ligne au TableLayout
        tableLayout.addView(tableRow);
    }

    // Méthode pour créer un TextView avec des styles réutilisables
    private TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.cell_border); // Utiliser un drawable pour les bordures
        return textView;
    }
}