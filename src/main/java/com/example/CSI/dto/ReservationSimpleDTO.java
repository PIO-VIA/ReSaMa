package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSimpleDTO {
    private Long numero;
    private LocalDate jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String motif;
    private String statut;
    private Integer nombreParticipants;
}