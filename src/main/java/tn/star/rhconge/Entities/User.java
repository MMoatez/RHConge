package tn.star.rhconge.Entities;



import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter

public class User {
    @Id
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    private int matricule;

    private String nom;

    private String prenom;

    private String email;

    private String password;

    @ManyToOne
    private Role role;

    private int congesRestants;

    private int congesPris;

    private int congesAnnuelsRestants;

    private int hAutorisation; // En minutes



    @ManyToOne
    private User manager;








    // Getters et setters

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getCongesRestants() {
        return congesRestants;
    }

    public void setCongesRestants(int congesRestants) {
        this.congesRestants = congesRestants;
    }

    public int getCongesPris() {
        return congesPris;
    }

    public void setCongesPris(int congesPris) {
        this.congesPris = congesPris;
    }

    public int getCongesAnnuelsRestants() {
        return congesAnnuelsRestants;
    }

    public void setCongesAnnuelsRestants(int congesAnnuelsRestants) {
        this.congesAnnuelsRestants = congesAnnuelsRestants;
    }

    public int getHAutorisation() {
        return hAutorisation;
    }

    public void setHAutorisation(int hAutorisation) {
        this.hAutorisation = hAutorisation;
    }


    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }


}
