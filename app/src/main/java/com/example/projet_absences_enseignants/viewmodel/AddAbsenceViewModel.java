package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.projet_absences_enseignants.model.Absence;

public class AddAbsenceViewModel extends AndroidViewModel {
    private MutableLiveData<Absence> absenceLiveData;

    public AddAbsenceViewModel(Application application) {
        super(application);
        absenceLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Absence> getAbsenceLiveData() {
        return absenceLiveData;
    }

    // Cette méthode va être appelée pour sauvegarder l'absence
    public void saveAbsence(String date, String heure, String salle, String classe, String enseignement) {
        // Générer un ID Agent automatique (par exemple, un UUID)
        String agentID = generateAgentID();

        Absence absence = new Absence(date, heure, salle, classe, enseignement, agentID);
        absenceLiveData.setValue(absence);
    }

    private String generateAgentID() {
        // Pour l'exemple, utilisons un UUID
        return java.util.UUID.randomUUID().toString();
    }
}
