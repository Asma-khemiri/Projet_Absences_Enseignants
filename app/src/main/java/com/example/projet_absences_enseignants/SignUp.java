package com.example.projet_absences_enseignants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private Button signupButton;
    private TextView loginLink;
    private ProgressBar progressBar;
    private Spinner roleSpinner; // Spinner pour choisir le rôle

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialisation des vues
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signupButton = findViewById(R.id.signupButton);
        loginLink = findViewById(R.id.loginLink);
        progressBar = findViewById(R.id.progressBar);
        roleSpinner = findViewById(R.id.roleSpinner); // Initialiser le Spinner

        // Initialisation de Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Remplir le Spinner avec les rôles définis dans le fichier strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Redirection vers la page de connexion
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, Login.class)); // Redirige vers LoginActivity
        });

        // Action pour le bouton d'inscription
        signupButton.setOnClickListener(v -> createUser());
    }

    private void createUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString(); // Récupérer le rôle sélectionné

        // Vérification que tous les champs sont remplis
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUp.this, "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Affichage de la ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Création de l'utilisateur avec Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Récupérer l'ID de l'utilisateur
                        String userId = mAuth.getCurrentUser().getUid();

                        // Ajouter le rôle et les informations utilisateur dans Firestore
                        User user = new User(name, email, role);
                        db.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(SignUp.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, Login.class)); // Redirection vers la page de connexion
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(SignUp.this, "Erreur lors de l'ajout du rôle", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // En cas d'échec de l'inscription
                        Toast.makeText(SignUp.this, "Échec de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}