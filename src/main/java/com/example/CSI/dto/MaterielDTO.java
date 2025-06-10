package com.example.CSI.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrdinateurDTO.class, name = "ORDINATEUR"),
        @JsonSubTypes.Type(value = VideoProjecteurDTO.class, name = "VIDEO_PROJECTEUR")
})
public abstract class MaterielDTO {
    private String codeMateriel;
    private Boolean disponibilite;
    private String marque;
    private String modele;
    private String etat;
    private String dateAcquisition;
    private String localisation;
    private List<ReservationSimpleDTO> reservations;
}