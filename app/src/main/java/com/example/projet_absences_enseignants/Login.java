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

import com.example.projet_absences_enseignants.MainActivity;
import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.SignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView signupLink;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialiser les vues
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupLink = findViewById(R.id.signupLink);
        progressBar = findViewById(R.id.progressBar);

        // Initialiser Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Rediriger vers la page d'inscription
        signupLink.setOnClickListener(v -> {
            // Rediriger vers SignUpActivity
            startActivity(new Intent(Login.this, SignUp.class));
        });


        // Action pour le bouton de connexion
        loginButton.setOnClickListener(v -> {
            loginUser();
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Connexion réussie
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Rediriger selon le rôle de l'utilisateur
                        // Ex: rediriger vers une page d'accueil
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    } else {
                        // Échec de la connexion
                        Toast.makeText(Login.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
