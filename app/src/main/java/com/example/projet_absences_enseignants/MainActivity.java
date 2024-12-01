package com.example.projet_absences_enseignants;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projet_absences_enseignants.view.HomeAgentDeSuiviFragment;
import com.example.projet_absences_enseignants.HomeTeacherFragment;
import com.example.projet_absences_enseignants.HomeAdminFragment;
import com.example.projet_absences_enseignants.viewmodel.AuthViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private BottomNavigationView bottomNavigationView;
    private String role; // Déclaration du rôle utilisateur

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser le ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Initialiser le BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Observer le rôle de l'utilisateur
        authViewModel.getUserRole().observe(this, userRole -> {
            if (userRole != null) {
                role = userRole; // Assigner le rôle à la variable `role`
                if (role.equals("Erreur")) {
                    Toast.makeText(MainActivity.this, "Erreur : Rôle introuvable", Toast.LENGTH_SHORT).show();
                } else {
                    // Naviguer vers le fragment correspondant au rôle
                    navigateBasedOnRole(role);
                }
            } else {
                Toast.makeText(MainActivity.this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            }
        });

        // Observer l'état de chargement
        authViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                // Afficher un indicateur de chargement (par exemple un ProgressBar)
            }
        });

        // Ajouter le listener pour le BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (role != null) {
                    if (item.getItemId() == R.id.home) {
                        // Naviguer vers la page d'accueil en fonction du rôle
                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new HomeAgentDeSuiviFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new HomeTeacherFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new HomeAdminFragment());
                        }
                    } else if (item.getItemId() == R.id.profile) {
                        // Naviguer vers la page de profil en fonction du rôle
                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new HomeAgentDeSuiviFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new HomeTeacherFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new HomeAdminFragment());
                        }
                    } else if (item.getItemId() == R.id.settings) {
                        // Naviguer vers les paramètres en fonction du rôle
                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new HomeAdminFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new HomeTeacherFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new HomeAdminFragment());
                        }
                    }
                }
                return true;  // Indiquer que l'élément a été traité
            }
        });
    }

    private void navigateBasedOnRole(String role) {
        Fragment fragment;

        // Selon le rôle, charger un fragment spécifique
        switch (role) {
            case "Agent de Suivi":
                fragment = new HomeAgentDeSuiviFragment();
                bottomNavigationView.setVisibility(View.VISIBLE); // Afficher le BottomNavigationView
                break;

            case "Administration":
                fragment = new HomeAdminFragment();
                bottomNavigationView.setVisibility(View.INVISIBLE); // Masquer le BottomNavigationView
                break;

            case "Enseignant":
                fragment = new HomeTeacherFragment();
                bottomNavigationView.setVisibility(View.VISIBLE); // Afficher le BottomNavigationView
                break;

            default:
                Toast.makeText(MainActivity.this, "Rôle non défini", Toast.LENGTH_SHORT).show();
                return;
        }

        // Remplacer le fragment actuel par le nouveau fragment
        replaceFragment(fragment);
    }

    // Méthode pour remplacer dynamiquement le fragment dans l'Activity
    private void replaceFragment(Fragment fragment) {
        // Remplacer dynamiquement le fragment dans l'Activity
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment); // Assurez-vous que 'frame_layout' est l'ID de votre conteneur de fragments
        transaction.commit();
    }
}
