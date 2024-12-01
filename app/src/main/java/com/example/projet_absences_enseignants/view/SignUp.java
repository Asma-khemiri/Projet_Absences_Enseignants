package com.example.projet_absences_enseignants.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.SignUpViewModel;

public class SignUp extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private Button signupButton;
    private TextView loginLink;
    private ProgressBar progressBar;
    private Spinner roleSpinner;

    private SignUpViewModel signUpViewModel;

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
        roleSpinner = findViewById(R.id.roleSpinner);

        // Initialisation du ViewModel
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        // Remplir le Spinner avec les rôles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Observer les messages de succès et d'erreur
        signUpViewModel.getSuccessMessage().observe(this, message -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUp.this, Login.class));
            finish();
        });

        signUpViewModel.getErrorMessage().observe(this, message -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
        });

        // Redirection vers la page de connexion
        loginLink.setOnClickListener(v -> startActivity(new Intent(SignUp.this, Login.class)));

        // Action pour le bouton d'inscription
        signupButton.setOnClickListener(v -> createUser());
    }

    private void createUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();

        // Vérification que tous les champs sont remplis
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUp.this, "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification du format de l'email
        if (!isValidEmail(email)) {
            Toast.makeText(SignUp.this, "L'email n'est pas valide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Affichage de la ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Demander au ViewModel de créer l'utilisateur
        signUpViewModel.createUser(name, email, password, role);
    }

    // Fonction pour vérifier si l'email est valide
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
