package com.example.projet_absences_enseignants;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private Button loginButton;
    private TextView signUpLink;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialiser FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Lier les éléments de l'interface utilisateur
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signUpLink = findViewById(R.id.signUpLink);

        // Ajouter un listener au bouton Connexion
        loginButton.setOnClickListener(view -> loginUser());

        // Ajouter un listener au lien d'inscription
        signUpLink.setOnClickListener(view -> {
            // Rediriger vers l'activité d'inscription
            startActivity(new Intent(Login.this, SignUp.class));
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Veuillez entrer un email !");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Veuillez entrer un mot de passe !");
            return;
        }

        // Se connecter avec FirebaseAuth
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Connexion réussie
                        Toast.makeText(Login.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();

                        // Rediriger vers la page principale de l'application
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    } else {
                        // Connexion échouée
                        Toast.makeText(Login.this, "Échec de la connexion : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
