package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.EmploiDuTempsAgentFragment;
import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.RapportsAgentFragment;
import com.example.projet_absences_enseignants.viewmodel.AgentViewModel;

public class HomeAgentDeSuiviFragment extends Fragment {

    private AgentViewModel agentViewModel;
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

        // Initialiser le ViewModel
        agentViewModel = new ViewModelProvider(this).get(AgentViewModel.class);

        // Définir les actions des CardViews
        cardHome.setOnClickListener(v -> agentViewModel.onNavigateTo("home"));
        cardAddAbsence.setOnClickListener(v -> agentViewModel.onNavigateTo("add_absence"));
        cardGAbsence.setOnClickListener(v -> agentViewModel.onNavigateTo("manage_absences"));
        cardCalendar.setOnClickListener(v -> agentViewModel.onNavigateTo("calendar"));
        cardRapport.setOnClickListener(v -> agentViewModel.onNavigateTo("report"));

        // Observer les événements de navigation
        agentViewModel.getNavigationEvent().observe(getViewLifecycleOwner(), this::handleNavigation);

        return view;
    }

    // Gérer la navigation en fonction de l'événement
    private void handleNavigation(String destination) {
        Fragment fragment = null;

        switch (destination) {
            case "home":
                fragment = new HomeAgentDeSuiviFragment();
                break;
            case "add_absence":
                fragment = new AddAbsenceFragment();
                break;
            case "manage_absences":
                fragment = new GestionAbsenceAgentFragment();
                break;
            case "calendar":
                fragment = new EmploiDuTempsAgentFragment();
                break;
            case "report":
                fragment = new RapportsAgentFragment();
                break;
        }

        if (fragment != null) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
    }
}
