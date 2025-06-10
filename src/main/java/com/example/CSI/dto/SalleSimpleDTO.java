package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalleSimpleDTO {
    private String codeSalle;
    private String nomSalle;
    private Integer capacite;
    private Boolean disponibilite;
    private String typeSalle;
    private String batiment;
    private String etage;
    private String equipements;
}