package com.example.CSI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "materiels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_materiel", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Materiel {

    @Id
    @Column(name = "code_materiel")
    @NotBlank(message = "Le code matériel ne peut pas être vide")
    private String codeMateriel;

    @Column(name = "disponibilite", nullable = false)
    private Boolean disponibilite = true;

    @Column(name = "marque")
    private String marque;

    @Column(name = "modele")
    private String modele;

    @Column(name = "etat")
    private String etat; // Bon, Moyen, Défaillant

    @Column(name = "date_acquisition")
    private String dateAcquisition;

    @Column(name = "localisation")
    private String localisation; // Où est stocké le matériel

    // Relations
    @OneToMany(mappedBy = "materiel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    // Constructeur sans les relations
    public Materiel(String codeMateriel, String marque, String modele, String etat, String dateAcquisition, String localisation) {
        this.codeMateriel = codeMateriel;
        this.disponibilite = true;
        this.marque = marque;
        this.modele = modele;
        this.etat = etat;
        this.dateAcquisition = dateAcquisition;
        this.localisation = localisation;
    }
}