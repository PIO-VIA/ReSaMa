package com.example.CSI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableauBordResponsableDTO {
    private int nombreFormations;
    private int nombreEnseignantsTotal;
    private int nombreEnseignantsNonResponsables;
    private List<FormationSimpleDTO> formations;
    private List<EnseignantSimpleDTO> enseignants;
}