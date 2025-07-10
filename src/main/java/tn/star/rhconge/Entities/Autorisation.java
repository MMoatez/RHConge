package tn.star.rhconge.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Autorisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String motif;

    private LocalDateTime dateSortie;

    private String duree;

    private String description;

    // Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getMotif() { return motif; }

    public void setMotif(String motif) { this.motif = motif; }

    public LocalDateTime getDateSortie() { return dateSortie; }

    public void setDateSortie(LocalDateTime dateSortie) { this.dateSortie = dateSortie; }

    public String getDuree() { return duree; }

    public void setDuree(String duree) { this.duree = duree; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
