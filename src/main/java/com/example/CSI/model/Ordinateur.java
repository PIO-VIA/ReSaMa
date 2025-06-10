package com.example.CSI.model;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ORDINATEUR")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Ordinateur extends Materiel {

    @Column(name = "processeur")
    private String processeur;

    @Column(name = "ram")
    private String ram;

    @Column(name = "stockage")
    private String stockage;

    @Column(name = "taille_ecran")
    private String tailleEcran;

    @Column(name = "systeme_exploitation")
    private String systemeExploitation;

    @Column(name = "type_ordinateur") // Portable, Bureau, etc.
    private String typeOrdinateur;

    // Constructeur
    public Ordinateur(String codeMateriel, String marque, String modele, String etat,
                      String dateAcquisition, String localisation, String processeur,
                      String ram, String stockage, String tailleEcran,
                      String systemeExploitation, String typeOrdinateur) {
        super(codeMateriel, marque, modele, etat, dateAcquisition, localisation);
        this.processeur = processeur;
        this.ram = ram;
        this.stockage = stockage;
        this.tailleEcran = tailleEcran;
        this.systemeExploitation = systemeExploitation;
        this.typeOrdinateur = typeOrdinateur;
    }
}