package com.example.CSI.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrdinateurSimpleDTO.class, name = "ORDINATEUR"),
        @JsonSubTypes.Type(value = VideoProjecteurSimpleDTO.class, name = "VIDEO_PROJECTEUR")
})
public abstract class MaterielSimpleDTO {
    private String codeMateriel;
    private Boolean disponibilite;
    private String marque;
    private String modele;
    private String etat;
    private String dateAcquisition;
    private String localisation;
}