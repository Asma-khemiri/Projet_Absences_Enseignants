package com.example.projet_absences_enseignants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

import org.checkerframework.checker.units.qual.A;

public class Home_agentDeSuivi extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private CardView cardHome, cardAddAbsence, cardGAbsence, cardCalendar, cardRapport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Charger le layout du fragment
        View view = inflater.inflate(R.layout.fragment_home_agent_de_suivi, container, false);

        // Initialiser les CardViews
        cardHome = view.findViewById(R.id.cardHome);
        cardAddAbsence = view.findViewById(R.id.cardAddAbsence);
        cardGAbsence = view.findViewById(R.id.cardGAbsence);
        cardCalendar = view.findViewById(R.id.cardCalendar);
        cardRapport = view.findViewById(R.id.cardRapport);

        // Initialiser le BottomNavigationView

        // Définir les actions des CardViews
        cardHome.setOnClickListener(v -> replaceFragment(new Home_agentDeSuivi()));
        cardAddAbsence.setOnClickListener(v -> replaceFragment(new AjouterAbsenceAgentFragment()));
        cardGAbsence.setOnClickListener(v -> replaceFragment(new GestionAbsencesAgentFragment()));
        cardCalendar.setOnClickListener(v -> replaceFragment(new EmploiDuTempsAgentFragment()));
        cardRapport.setOnClickListener(v -> replaceFragment(new RapportsAgentFragment()));

        // Associer le menu au BottomNavigationView


        return view;
    }

    // Remplacer le fragment actuel par le nouveau
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
