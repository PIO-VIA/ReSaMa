package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantSimpleDTO {
    private Long idEnseignant;
    private String nomEnseignant;
    private String prenomEnseignant;
    private String email;
    private String telephone;
    private String specialite;
}