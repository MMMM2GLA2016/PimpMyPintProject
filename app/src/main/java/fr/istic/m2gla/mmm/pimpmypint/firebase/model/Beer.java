package fr.istic.m2gla.mmm.pimpmypint.firebase.model;

import fr.istic.m2gla.mmm.pimpmypint.BeerCursorAdapter;

public class Beer {
    private String nom;
    private String brasserie;
    private Double alcool;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBrasserie() {
        return brasserie;
    }

    public void setBrasserie(String brasserie) {
        this.brasserie = brasserie;
    }

    public Double getAlcool() {
        return alcool;
    }

    public void setAlcool(Double alcool) {
        this.alcool = alcool;
    }
}
