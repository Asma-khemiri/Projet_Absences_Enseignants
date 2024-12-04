package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;
import com.example.projet_absences_enseignants.viewmodel.RapportAbsenceViewModel;

import java.util.List;

public class RapportAbsenceFragment extends Fragment {

    private WebView webView;
    private RapportAbsenceViewModel absenceViewModel;
    private String embedUrl = "<PowerBI_Embed_URL>";  // URL d'embarquement de ton rapport Power BI
    private String accessToken = "<PowerBI_Embed_Token>";  // Token d'embarquement Power BI

    public RapportAbsenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_rapport_absence, container, false);

        // Initialiser la WebView
        webView = rootView.findViewById(R.id.webViewPowerBI);
        webView.getSettings().setJavaScriptEnabled(true);  // Activer JavaScript pour le rapport Power BI

        // Initialiser le ViewModel
        absenceViewModel = new ViewModelProvider(requireActivity()).get(RapportAbsenceViewModel.class);

        // Observer les absences (les données de Firestore)
        absenceViewModel.getAbsences().observe(getViewLifecycleOwner(), new Observer<List<Absence>>() {
            @Override
            public void onChanged(List<Absence> absences) {
                // Cette méthode est appelée chaque fois que les données changent dans Firestore
                Log.d("AbsenceFragment", "Données d'absences mises à jour : " + absences.size());

                // Mise à jour ou traitement des absences ici si nécessaire (pas nécessaire pour le rapport Power BI)
            }
        });

        // Charger le rapport Power BI dans la WebView
        String embedUrlWithToken = embedUrl + "?access_token=" + accessToken;
        webView.loadUrl(embedUrlWithToken);

        return rootView;
    }
}
