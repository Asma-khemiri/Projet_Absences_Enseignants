package com.example.projet_absences_enseignants;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_absences_enseignants.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de Firebase Firestore
        db = FirebaseFirestore.getInstance();
    }
}
