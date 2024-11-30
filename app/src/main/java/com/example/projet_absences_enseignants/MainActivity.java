package com.example.projet_absences_enseignants;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialiser le BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Vérifier si un utilisateur est connecté
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // L'utilisateur est connecté, vérifier son rôle
            checkUserRole(user.getUid());
        } else {
            // Utilisateur non connecté, rediriger vers la page de connexion
            Toast.makeText(MainActivity.this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
        }


    }

    private void checkUserRole(String userId) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        navigateBasedOnRole(role);
                    } else {
                        Toast.makeText(MainActivity.this, "Erreur : Utilisateur sans rôle", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Erreur de récupération du rôle", Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateBasedOnRole(String role) {
        Fragment fragment;

        // Selon le rôle, charger un fragment d'accueil, de profil et de paramètres différents
        switch (role) {
            case "Agent de Suivi":
                fragment = new Home_agentDeSuivi(); // Fragment spécifique pour l'Agent de Suivi
                bottomNavigationView.setVisibility(View.VISIBLE); // Afficher le BottomNavigationView
                break;

            case "Administration":
                fragment = new HomeAdminFragment(); // Fragment pour l'administration
                bottomNavigationView.setVisibility(View.INVISIBLE); // Masquer le BottomNavigationView
                break;

            case "Enseignant":
                fragment = new HomeTeacherFragment(); // Fragment spécifique pour l'Enseignant
                bottomNavigationView.setVisibility(View.VISIBLE); // Afficher le BottomNavigationView
                break;

            default:
                Toast.makeText(MainActivity.this, "Rôle non défini", Toast.LENGTH_SHORT).show();
                return;
        }

        // Remplacer le fragment actuel par le nouveau fragment
        replaceFragment(fragment);


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    if (role.equals("Agent de Suivi")) {
                        replaceFragment(new Home_agentDeSuivi());
                    } else if (role.equals("Enseignant")) {
                        replaceFragment(new HomeTeacherFragment());
                    }
                    else if (role.equals("Administration")) {
                        replaceFragment(new HomeAdminFragment());}
                } else if (item.getItemId() == R.id.profile) {
                    if (role.equals("Agent de Suivi")) {
                        replaceFragment(new Home_agentDeSuivi());
                    } else if (role.equals("Enseignant")) {
                        replaceFragment(new HomeAdminFragment());
                    } else if (role.equals("Administration")) {
                        replaceFragment(new HomeAdminFragment());
                    }
                } else if (item.getItemId() == R.id.settings) {
                    if (role.equals("Agent de Suivi")) {
                        replaceFragment(new HomeAdminFragment());
                    } else if (role.equals("Enseignant")) {
                        replaceFragment(new HomeAdminFragment());
                    } else if (role.equals("Administration")) {
                        replaceFragment(new HomeAdminFragment());
                    }
                }
                return true;  // Indiquer que l'élément a été traité
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        // Remplacer dynamiquement le fragment dans l'Activity
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment); // R.id.frame_layout doit être l'ID de votre conteneur de fragments dans le layout XML
        transaction.commit();
    }
}
