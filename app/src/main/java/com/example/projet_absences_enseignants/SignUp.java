package com.example.projet_absences_enseignants;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput, confirmPasswordInput;
    private Button signUpButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialiser FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Lier les éléments de l'interface utilisateur
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput); // Ajout pour confirmer le mot de passe
        signUpButton = findViewById(R.id.signUpButton);

        // Ajouter un listener au bouton Inscription
        signUpButton.setOnClickListener(view -> signUpUser());
    }

    private void signUpUser() {
        // Récupérer les données saisies par l'utilisateur
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim(); // Confirmation du mot de passe

        // Vérification des champs
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Veuillez entrer un email !");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Veuillez entrer un mot de passe !");
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Le mot de passe doit contenir au moins 6 caractères !");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordInput.setError("Veuillez confirmer votre mot de passe !");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Les mots de passe ne correspondent pas !");
            return;
        }

        // Afficher un message avant la création du compte
        signUpButton.setEnabled(false); // Désactiver le bouton pour éviter plusieurs soumissions
        Toast.makeText(SignUp.this, "Création du compte en cours...", Toast.LENGTH_SHORT).show();

        // Inscription avec FirebaseAuth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    signUpButton.setEnabled(true); // Réactiver le bouton après l'opération

                    if (task.isSuccessful()) {
                        // Succès de l'inscription
                        Toast.makeText(SignUp.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();

                        // Rediriger vers une autre activité (par exemple, la page de connexion)
                        startActivity(new Intent(SignUp.this, Login.class));
                        finish();
                    } else {
                        // Échec de l'inscription
                        Toast.makeText(SignUp.this, "Échec de l'inscription : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
