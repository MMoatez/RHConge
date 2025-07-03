package tn.star.rhconge.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    private int id;

    @OneToOne
    @JoinColumn(name = "id_demande", referencedColumnName = "id") //, unique = true, nullable = false
    private Demande demande;

    private int nbJour;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "date_debut_id")
    private Datee dateDebut ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "date_fin_id")
    private Datee dateFin ;

    @OneToOne(cascade = CascadeType.ALL)
    private TypeC type;  // Repos, MariageAgent, Naissance, Circoncision, MariageEnfant, DecesParents, DecesConjointEnfant, DecesAutre












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

    public int getNbJour() {
        return nbJour;
    }

    public void setNbJour(int nbJour) {
        this.nbJour = nbJour;
    }

    public Datee getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Datee dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Datee getDateFin() {
        return dateFin;
    }

    public void setDateFin(Datee dateFin) {
        this.dateFin = dateFin;
    }

    public TypeC getType() {
        return type;
    }

    public void setType(TypeC type) {
        this.type = type;
    }

}
