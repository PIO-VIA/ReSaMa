package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationDTO {
    private Long idFormation;
    private String codeFormation;
    private String nomFormation;
    private String description;
    private String niveau;
    private Integer dureeHeures;
    private EnseignantSimpleDTO responsable;
    private List<ReservationSimpleDTO> reservations;
}