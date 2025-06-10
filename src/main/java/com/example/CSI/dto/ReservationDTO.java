package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long numero;
    private LocalDate jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String motif;
    private String statut;
    private Integer nombreParticipants;
    private EnseignantSimpleDTO enseignant;
    private SalleSimpleDTO salle;
    private MaterielSimpleDTO materiel;
    private FormationSimpleDTO formation;
}