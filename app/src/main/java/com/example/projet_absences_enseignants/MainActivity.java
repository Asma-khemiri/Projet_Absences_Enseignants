package com.example.projet_absences_enseignants;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projet_absences_enseignants.view.HomeAdminFragment;
import com.example.projet_absences_enseignants.view.HomeAgentDeSuiviFragment;
import com.example.projet_absences_enseignants.viewmodel.AuthViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private BottomNavigationView bottomNavigationView;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        authViewModel.getUserRole().observe(this, userRole -> {
            if (userRole != null) {
                role = userRole;
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




        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (role != null) {
                    if (item.getItemId() == R.id.home) {

                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new HomeAgentDeSuiviFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new HomeTeacherFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new HomeAdminFragment());
                        }
                    } else if (item.getItemId() == R.id.profile) {

                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new HomeAgentDeSuiviFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new HomeTeacherFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new HomeTeacherFragment());
                        }
                    } else if (item.getItemId() == R.id.settings) {

                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new HomeAdminFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new HomeTeacherFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new HomeAdminFragment());
                        }
                    }
                }
                return true;
            }
        });
    }

    private void navigateBasedOnRole(String role) {
        Fragment fragment;

        // Selon le rôle, charger un fragment spécifique
        switch (role) {
            case "Agent de Suivi":
                fragment = new HomeAgentDeSuiviFragment();
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;

            case "Administration":
                fragment = new HomeAdminFragment();
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;

            case "Enseignant":
                fragment = new HomeTeacherFragment();
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;

            default:
                Toast.makeText(MainActivity.this, "Rôle non défini", Toast.LENGTH_SHORT).show();
                return;
        }

        // Remplacer le fragment actuel par le nouveau fragment
        replaceFragment(fragment);
    }


    private void replaceFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
