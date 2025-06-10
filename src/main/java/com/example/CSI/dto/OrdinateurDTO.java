package com.example.CSI.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrdinateurDTO extends MaterielDTO {
    private String processeur;
    private String ram;
    private String stockage;
    private String tailleEcran;
    private String systemeExploitation;
    private String typeOrdinateur;
}