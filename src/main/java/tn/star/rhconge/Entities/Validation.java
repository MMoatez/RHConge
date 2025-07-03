package tn.star.rhconge.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter


public class Validation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private Boolean approuve;

    private LocalDateTime dateV;

    private String description;

    @ManyToOne
    @JoinColumn(name = "id_demande", referencedColumnName = "id")
    private Demande idDemande;

    @OneToOne
    @JoinColumn(name = "matricule_validateur", referencedColumnName = "matricule")
    private User matriculeValidateur;





    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getApprouve() {
        return approuve;
    }

    public void setApprouve(Boolean approuve) {
        this.approuve = approuve;
    }

    public LocalDateTime getDateV() {
        return dateV;
    }

    public void setDateV(LocalDateTime dateV) {
        this.dateV = dateV;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Demande getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Demande idDemande) {
        this.idDemande = idDemande;
    }

    public User getMatriculeValidateur() {
        return matriculeValidateur;
    }

    public void setMatriculeValidateur(User matriculeValidateur) {
        this.matriculeValidateur = matriculeValidateur;
    }




}
