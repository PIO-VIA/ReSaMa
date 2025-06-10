package com.example.CSI.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "formations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formation")
    private Long idFormation;

    @NotBlank(message = "Le code formation ne peut pas être vide")
    @Column(name = "code_formation", unique = true, nullable = false)
    private String codeFormation;

    @NotBlank(message = "Le nom de la formation ne peut pas être vide")
    @Column(name = "nom_formation", nullable = false)
    private String nomFormation;

    @Column(name = "description")
    private String description;

    @Column(name = "niveau")
    private String niveau;

    @Column(name = "duree_heures")
    private Integer dureeHeures;

    // Relation avec l'enseignant responsable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable", nullable = false)
    @NotNull(message = "Une formation doit avoir un responsable")
    private Enseignant responsable;

    // Relation avec les réservations (optionnel)
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    // Constructeur sans les relations
    public Formation(String codeFormation, String nomFormation, String description, String niveau, Integer dureeHeures, Enseignant responsable) {
        this.codeFormation = codeFormation;
        this.nomFormation = nomFormation;
        this.description = description;
        this.niveau = niveau;
        this.dureeHeures = dureeHeures;
        this.responsable = responsable;
    }
}
