package tn.star.rhconge.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.REMOVE) //cascade = CascadeType.ALL
    @JoinColumn(name = "id_autorisation", referencedColumnName = "id")
    private Autorisation autorisation;

    @OneToOne(cascade = CascadeType.REMOVE)  //cascade = CascadeType.ALL
    @JoinColumn(name = "id_conge", referencedColumnName = "id")
    private Conge conge;

    @OneToOne
    @JoinColumn(name = "status", referencedColumnName = "id")
    private Status status;

    private int nbValidateur;

    private LocalDateTime dateModif;

    @ManyToOne
    @JoinColumn(name = "matricule_demandeur", referencedColumnName = "matricule")
    private User matriculeDemandeur;

    // Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Autorisation getAutorisation() { return autorisation; }

    public void setAutorisation(Autorisation autorisation) { this.autorisation = autorisation; }

    public Conge getConge() { return conge; }

    public void setConge(Conge conge) { this.conge = conge; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public int getNbValidateur() { return nbValidateur; }

    public void setNbValidateur(int nbValidateur) { this.nbValidateur = nbValidateur; }

    public LocalDateTime getDateModif() { return dateModif; }

    public void setDateModif(LocalDateTime dateModif) { this.dateModif = dateModif; }

    public User getMatriculeDemandeur() { return matriculeDemandeur; }

    public void setMatriculeDemandeur(User matriculeDemandeur) { this.matriculeDemandeur = matriculeDemandeur; }
}
