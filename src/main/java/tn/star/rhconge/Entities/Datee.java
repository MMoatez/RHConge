package tn.star.rhconge.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Datee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime date;

    private boolean matin;

    private boolean apresMidi;

    // Getters et Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public boolean isMatin() { return matin; }

    public void setMatin(boolean matin) { this.matin = matin; }

    public boolean isApresMidi() { return apresMidi; }

    public void setApresMidi(boolean apresMidi) { this.apresMidi = apresMidi; }
}
