package com.example.projet_absences_enseignants.model;

import java.io.Serializable;

public class Absence implements Serializable {
    private String date;
    private String heure;
    private String classe;
    private String enseignement;
    private String statut;
    private String agentID;
    private String absenceID;

    public Absence() {
    }

    public Absence(String date, String heure, String classe, String enseignement, String statut, String agentID,String absenceID) {
        this.date = date;
        this.heure = heure;
        this.classe = classe;
        this.enseignement = enseignement;
        this.statut = statut;
        this.agentID = agentID;
        this.absenceID=absenceID;

    }

    public String getDate() {
        return date;
    }
    public String getAbsenceID() {
        return absenceID;
    }
    public void setAbsenceID(String absenceID) {
        this.absenceID = absenceID;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
}