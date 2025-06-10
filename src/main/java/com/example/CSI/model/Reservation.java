package com.example.CSI.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Long numero;

    @NotNull(message = "La date ne peut pas être nulle")
    @Column(name = "jour", nullable = false)
    private LocalDate jour;

    @NotNull(message = "L'heure de début ne peut pas être nulle")
    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin ne peut pas être nulle")
    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @Column(name = "motif")
    private String motif;

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutReservation statut = StatutReservation.CONFIRMEE;

    @Column(name = "nombre_participants")
    private Integer nombreParticipants;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_enseignant", nullable = false)
    @NotNull(message = "Une réservation doit avoir un enseignant")
    private Enseignant enseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_salle", nullable = false)
    @NotNull(message = "Une réservation doit avoir une salle")
    private Salle salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_materiel")
    private Materiel materiel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formation")
    private Formation formation;

    // Constructeur principal
    public Reservation(LocalDate jour, LocalTime heureDebut, LocalTime heureFin,
                       String motif, Integer nombreParticipants, Enseignant enseignant,
                       Salle salle, Materiel materiel, Formation formation) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.motif = motif;
        this.nombreParticipants = nombreParticipants;
        this.enseignant = enseignant;
        this.salle = salle;
        this.materiel = materiel;
        this.formation = formation;
        this.statut = StatutReservation.CONFIRMEE;
    }

    // Enum pour le statut
    public enum StatutReservation {
        CONFIRMEE, ANNULEE, EN_ATTENTE
    }
}