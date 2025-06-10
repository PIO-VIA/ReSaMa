package com.example.CSI.controller;


import com.example.CSI.model.*;
import com.example.CSI.service.MaterielService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/materiels")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MaterielController {

    private final MaterielService materielService;

    // Créer un nouveau matériel
    @PostMapping
    public ResponseEntity<Materiel> creerMateriel(@Valid @RequestBody Materiel materiel) {
        log.info("Demande de création de matériel: {}", materiel.getCodeMateriel());

        try {
            Materiel nouveauMateriel = materielService.creerMateriel(materiel);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauMateriel);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création du matériel: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Créer un nouvel ordinateur
    @PostMapping("/ordinateurs")
    public ResponseEntity<Materiel> creerOrdinateur(@Valid @RequestBody Ordinateur ordinateur) {
        log.info("Demande de création d'ordinateur: {}", ordinateur.getCodeMateriel());

        try {
            Materiel nouvelOrdinateur = materielService.creerMateriel(ordinateur);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelOrdinateur);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de l'ordinateur: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Créer un nouveau vidéoprojecteur
    @PostMapping("/videoprojecteurs")
    public ResponseEntity<Materiel> creerVideoProjecteur(@Valid @RequestBody VideoProjecteur videoProjecteur) {
        log.info("Demande de création de vidéoprojecteur: {}", videoProjecteur.getCodeMateriel());

        try {
            Materiel nouveauVideoProjecteur = materielService.creerMateriel(videoProjecteur);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauVideoProjecteur);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création du vidéoprojecteur: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer tous les matériels
    @GetMapping
    public ResponseEntity<List<Materiel>> obtenirTousLesMateriels() {
        log.info("Demande de récupération de tous les matériels");
        List<Materiel> materiels = materielService.obtenirTousLesMateriels();
        return ResponseEntity.ok(materiels);
    }

    // Récupérer un matériel par code
    @GetMapping("/{codeMateriel}")
    public ResponseEntity<Materiel> obtenirMaterielParCode(@PathVariable String codeMateriel) {
        log.info("Demande de récupération du matériel avec le code: {}", codeMateriel);

        return materielService.obtenirMaterielParCode(codeMateriel)
                .map(materiel -> ResponseEntity.ok(materiel))
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les matériels disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Materiel>> obtenirMaterielsDisponibles() {
        log.info("Demande de récupération des matériels disponibles");
        List<Materiel> materiels = materielService.obtenirMaterielsDisponibles();
        return ResponseEntity.ok(materiels);
    }

    // Récupérer les ordinateurs uniquement
    @GetMapping("/ordinateurs")
    public ResponseEntity<List<Materiel>> obtenirOrdinateurs() {
        log.info("Demande de récupération des ordinateurs");
        List<Materiel> ordinateurs = materielService.obtenirOrdinateurs();
        return ResponseEntity.ok(ordinateurs);
    }

    // Récupérer les vidéoprojecteurs uniquement
    @GetMapping("/videoprojecteurs")
    public ResponseEntity<List<Materiel>> obtenirVideoProjecteurs() {
        log.info("Demande de récupération des vidéoprojecteurs");
        List<Materiel> videoProjecteurs = materielService.obtenirVideoProjecteurs();
        return ResponseEntity.ok(videoProjecteurs);
    }

    // Récupérer les matériels par marque
    @GetMapping("/marque/{marque}")
    public ResponseEntity<List<Materiel>> obtenirMaterielsParMarque(@PathVariable String marque) {
        log.info("Demande de récupération des matériels par marque: {}", marque);
        List<Materiel> materiels = materielService.obtenirMaterielsParMarque(marque);
        return ResponseEntity.ok(materiels);
    }

    // Récupérer les matériels par état
    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<Materiel>> obtenirMaterielsParEtat(@PathVariable String etat) {
        log.info("Demande de récupération des matériels par état: {}", etat);
        List<Materiel> materiels = materielService.obtenirMaterielsParEtat(etat);
        return ResponseEntity.ok(materiels);
    }

    // Récupérer les matériels par localisation
    @GetMapping("/localisation/{localisation}")
    public ResponseEntity<List<Materiel>> obtenirMaterielsParLocalisation(@PathVariable String localisation) {
        log.info("Demande de récupération des matériels par localisation: {}", localisation);
        List<Materiel> materiels = materielService.obtenirMaterielsParLocalisation(localisation);
        return ResponseEntity.ok(materiels);
    }

    // Récupérer les matériels disponibles pour une période
    @GetMapping("/disponibles/periode")
    public ResponseEntity<List<Materiel>> obtenirMaterielsDisponiblesPourPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate jour,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin) {
        log.info("Demande de récupération des matériels disponibles le {} de {} à {}", jour, heureDebut, heureFin);
        List<Materiel> materiels = materielService.obtenirMaterielsDisponiblesPourPeriode(jour, heureDebut, heureFin);
        return ResponseEntity.ok(materiels);
    }

    // Mettre à jour un matériel
    @PutMapping("/{codeMateriel}")
    public ResponseEntity<Materiel> mettreAJourMateriel(@PathVariable String codeMateriel,
                                                        @Valid @RequestBody Materiel materiel) {
        log.info("Demande de mise à jour du matériel avec le code: {}", codeMateriel);

        try {
            Materiel materielMisAJour = materielService.mettreAJourMateriel(codeMateriel, materiel);
            return ResponseEntity.ok(materielMisAJour);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la mise à jour du matériel: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Modifier la disponibilité d'un matériel
    @PatchMapping("/{codeMateriel}/disponibilite")
    public ResponseEntity<Materiel> modifierDisponibiliteMateriel(@PathVariable String codeMateriel,
                                                                  @RequestParam Boolean disponibilite) {
        log.info("Demande de modification de la disponibilité du matériel {} à {}", codeMateriel, disponibilite);

        try {
            Materiel materielModifie = materielService.modifierDisponibiliteMateriel(codeMateriel, disponibilite);
            return ResponseEntity.ok(materielModifie);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la modification de la disponibilité: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Modifier l'état d'un matériel
    @PatchMapping("/{codeMateriel}/etat")
    public ResponseEntity<Materiel> modifierEtatMateriel(@PathVariable String codeMateriel,
                                                         @RequestParam String etat) {
        log.info("Demande de modification de l'état du matériel {} à {}", codeMateriel, etat);

        try {
            Materiel materielModifie = materielService.modifierEtatMateriel(codeMateriel, etat);
            return ResponseEntity.ok(materielModifie);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la modification de l'état: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Déplacer un matériel
    @PatchMapping("/{codeMateriel}/localisation")
    public ResponseEntity<Materiel> deplacerMateriel(@PathVariable String codeMateriel,
                                                     @RequestParam String localisation) {
        log.info("Demande de déplacement du matériel {} vers {}", codeMateriel, localisation);

        try {
            Materiel materielDeplace = materielService.deplacerMateriel(codeMateriel, localisation);
            return ResponseEntity.ok(materielDeplace);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors du déplacement du matériel: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un matériel
    @DeleteMapping("/{codeMateriel}")
    public ResponseEntity<Void> supprimerMateriel(@PathVariable String codeMateriel) {
        log.info("Demande de suppression du matériel avec le code: {}", codeMateriel);

        try {
            materielService.supprimerMateriel(codeMateriel);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la suppression du matériel: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Vérifier si un matériel existe
    @GetMapping("/{codeMateriel}/existe")
    public ResponseEntity<Boolean> materielExiste(@PathVariable String codeMateriel) {
        log.info("Vérification de l'existence du matériel avec le code: {}", codeMateriel);
        boolean existe = materielService.materielExiste(codeMateriel);
        return ResponseEntity.ok(existe);
    }
}