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

import com.example.projet_absences_enseignants.Login;
import com.example.projet_absences_enseignants.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private Button signupButton;
    private TextView loginLink;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Assurez-vous que le nom du fichier XML est correct

        // Initialiser les vues
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signupButton = findViewById(R.id.signupButton);
        loginLink = findViewById(R.id.loginLink);
        progressBar = findViewById(R.id.progressBar);

        // Initialiser Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Rediriger vers la page de connexion lorsque l'utilisateur clique sur le lien
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, Login.class)); // Rediriger vers LoginActivity
        });

        // Action pour le bouton d'inscription
        signupButton.setOnClickListener(v -> {
            createUser();
        });
    }

    private void createUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUp.this, "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Inscription réussie
                        Toast.makeText(SignUp.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, Login.class)); // Retourner vers LoginActivity
                        finish();
                    } else {
                        // Échec de l'inscription
                        Toast.makeText(SignUp.this, "Échec de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
