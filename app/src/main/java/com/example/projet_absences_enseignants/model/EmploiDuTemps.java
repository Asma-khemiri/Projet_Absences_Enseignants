package com.example.projet_absences_enseignants.model;

import java.util.HashMap;
import java.util.Map;

public class EmploiDuTemps {
    private String jour;
    private String heureDebut;
    private String heureFin;
    private String matiere;

    // Constructeur avec des arguments
    public EmploiDuTemps(String jour, String heureDebut, String heureFin, String matiere) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.matiere = matiere;
    }

    // Getters et setters
    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    // Méthode pour convertir l'objet en un Map, compatible avec Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("jour", jour);
        map.put("heureDebut", heureDebut);
        map.put("heureFin", heureFin);
        map.put("matiere", matiere);
        return map;
    }
}
