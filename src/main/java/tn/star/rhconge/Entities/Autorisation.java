package tn.star.rhconge.Entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class Autorisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incrémenté
    private int id;

    @OneToOne
    @JoinColumn(name = "id_demande", referencedColumnName = "id") //, nullable = false, unique = true
    private Demande demande;

    private String motif;   //non enum

    private LocalDateTime dateSortie;

    private String duree;


    private String description;




    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public LocalDateTime getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDateTime dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
