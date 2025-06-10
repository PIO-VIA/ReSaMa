package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalleDTO {
    private String codeSalle;
    private String nomSalle;
    private Integer capacite;
    private Boolean disponibilite;
    private String typeSalle;
    private String batiment;
    private String etage;
    private String equipements;
    private List<ReservationSimpleDTO> reservations;
}