package com.example.projet_absences_enseignants.model;

public class Absence {
    private String date;
    private String heure;
    private String classe;
    private String enseignement;
    private String agentID;

    // Constructeur
    public Absence(String date, String heure, String classe, String enseignement, String agentID) {
        this.date = date;
        this.heure = heure;
        this.classe = classe;
        this.enseignement = enseignement;
        this.agentID = agentID;
    }

    // Getters et setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getEnseignement() {
        return enseignement;
    }

    public void setEnseignement(String enseignement) {
        this.enseignement = enseignement;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
}
