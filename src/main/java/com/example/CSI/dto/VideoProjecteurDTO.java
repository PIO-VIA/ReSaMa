package com.example.CSI.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VideoProjecteurDTO extends MaterielDTO {
    private String description;
    private String resolution;
    private String luminosite;
    private String connectivite;
    private Double poids;
    private String typeProjection;
}