package tn.star.rhconge.Entities;

import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    // ----- Getters -----

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    // ----- Setters -----

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
