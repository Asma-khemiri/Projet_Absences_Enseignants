package com.example.projet_absences_enseignants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView signupLink;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des vues
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupLink = findViewById(R.id.signupLink);
        progressBar = findViewById(R.id.progressBar);

        // Initialisation de Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Lien pour l'inscription
        signupLink.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, SignUp.class)); // Redirige vers SignUpActivity
        });

        // Action pour le bouton de connexion
        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Vérification des champs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Affichage de la ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Connexion via Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Connexion réussie
                        Toast.makeText(Login.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            checkUserRole(user.getUid()); // Vérifier le rôle dans Firestore
                        }
                    } else {
                        // En cas d'échec de la connexion
                        Toast.makeText(Login.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserRole(String userId) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        navigateBasedOnRole(role);
                    } else {
                        Toast.makeText(Login.this, "Utilisateur sans rôle", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Login.this, "Erreur de récupération du rôle", Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateBasedOnRole(String role) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.putExtra("ROLE", role);  // Passer le rôle de l'utilisateur à MainActivity
        startActivity(intent);
        finish(); // Terminer l'activité Login
    }
}
