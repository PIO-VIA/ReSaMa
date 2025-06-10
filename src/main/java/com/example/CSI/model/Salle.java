package com.example.CSI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "salles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {

    @Id
    @Column(name = "code_salle")
    @NotBlank(message = "Le code salle ne peut pas être vide")
    private String codeSalle;

    @NotBlank(message = "Le nom de la salle ne peut pas être vide")
    @Column(name = "nom_salle", nullable = false)
    private String nomSalle;

    @Positive(message = "La capacité doit être positive")
    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @Column(name = "disponibilite", nullable = false)
    private Boolean disponibilite = true;

    @Column(name = "type_salle")
    private String typeSalle; // Cours, TP, Amphi, etc.

    @Column(name = "batiment")
    private String batiment;

    @Column(name = "etage")
    private String etage;

    @Column(name = "equipements")
    private String equipements; // Liste des équipements de base de la salle

    // Relations
    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    // Constructeur sans les relations
    public Salle(String codeSalle, String nomSalle, Integer capacite, String typeSalle, String batiment, String etage, String equipements) {
        this.codeSalle = codeSalle;
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.disponibilite = true;
        this.typeSalle = typeSalle;
        this.batiment = batiment;
        this.etage = etage;
        this.equipements = equipements;
    }
}