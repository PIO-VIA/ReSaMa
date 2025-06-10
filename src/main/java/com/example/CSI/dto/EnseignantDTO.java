package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantDTO {
    private Long idEnseignant;
    private String nomEnseignant;
    private String prenomEnseignant;
    private String email;
    private String telephone;
    private String specialite;
    private List<FormationSimpleDTO> formationsResponsables;
    private List<ReservationSimpleDTO> reservations;
}