package tn.star.rhconge.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int nbJour;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "date_debut_id", referencedColumnName = "id")
    private Datee dateDebut;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "date_fin_id", referencedColumnName = "id")
    private Datee dateFin;

    //@OneToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeC type;

    // Getters et Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getNbJour() { return nbJour; }

    public void setNbJour(int nbJour) { this.nbJour = nbJour; }

    public Datee getDateDebut() { return dateDebut; }

    public void setDateDebut(Datee dateDebut) { this.dateDebut = dateDebut; }

    public Datee getDateFin() { return dateFin; }

    public void setDateFin(Datee dateFin) { this.dateFin = dateFin; }

    public TypeC getType() { return type; }

    public void setType(TypeC type) { this.type = type; }
}
