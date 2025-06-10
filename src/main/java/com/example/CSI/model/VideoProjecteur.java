package com.example.CSI.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("VIDEO_PROJECTEUR")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VideoProjecteur extends Materiel {

    @Column(name = "description")
    private String description;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "luminosite")
    private String luminosite; // en lumens

    @Column(name = "connectivite")
    private String connectivite; // HDMI, VGA, USB, etc.

    @Column(name = "poids")
    private Double poids;

    @Column(name = "type_projection") // LCD, DLP, etc.
    private String typeProjection;

    // Constructeur
    public VideoProjecteur(String codeMateriel, String marque, String modele, String etat,
                           String dateAcquisition, String localisation, String description,
                           String resolution, String luminosite, String connectivite,
                           Double poids, String typeProjection) {
        super(codeMateriel, marque, modele, etat, dateAcquisition, localisation);
        this.description = description;
        this.resolution = resolution;
        this.luminosite = luminosite;
        this.connectivite = connectivite;
        this.poids = poids;
        this.typeProjection = typeProjection;
    }
}
