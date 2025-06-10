package com.example.CSI.controller;

import com.example.CSI.dto.MaterielDTO;
import com.example.CSI.dto.MaterielSimpleDTO;
import com.example.CSI.mapper.DTOMapper;
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
    private final DTOMapper dtoMapper;

    // Créer un nouveau matériel
    @PostMapping
    public ResponseEntity<MaterielSimpleDTO> creerMateriel(@Valid @RequestBody Materiel materiel) {
        log.info("Demande de création de matériel: {}", materiel.getCodeMateriel());

        try {
            Materiel nouveauMateriel = materielService.creerMateriel(materiel);
            MaterielSimpleDTO dto = dtoMapper.toMaterielSimpleDTO(nouveauMateriel);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création du matériel: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Créer un nouvel ordinateur
    @PostMapping("/ordinateurs")
    public ResponseEntity<MaterielSimpleDTO> creerOrdinateur(@Valid @RequestBody Ordinateur ordinateur) {
        log.info("Demande de création d'ordinateur: {}", ordinateur.getCodeMateriel());

        try {
            Materiel nouvelOrdinateur = materielService.creerMateriel(ordinateur);
            MaterielSimpleDTO dto = dtoMapper.toMaterielSimpleDTO(nouvelOrdinateur);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de l'ordinateur: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Créer un nouveau vidéoprojecteur
    @PostMapping("/videoprojecteurs")
    public ResponseEntity<MaterielSimpleDTO> creerVideoProjecteur(@Valid @RequestBody VideoProjecteur videoProjecteur) {
        log.info("Demande de création de vidéoprojecteur: {}", videoProjecteur.getCodeMateriel());

        try {
            Materiel nouveauVideoProjecteur = materielService.creerMateriel(videoProjecteur);
            MaterielSimpleDTO dto = dtoMapper.toMaterielSimpleDTO(nouveauVideoProjecteur);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création du vidéoprojecteur: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer tous les matériels
    @GetMapping
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirTousLesMateriels() {
        log.info("Demande de récupération de tous les matériels");
        List<Materiel> materiels = materielService.obtenirTousLesMateriels();
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(materiels);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer un matériel par code (avec détails et réservations)
    @GetMapping("/{codeMateriel}")
    public ResponseEntity<MaterielDTO> obtenirMaterielParCode(@PathVariable String codeMateriel) {
        log.info("Demande de récupération du matériel avec le code: {}", codeMateriel);

        return materielService.obtenirMaterielParCode(codeMateriel)
                .map(materiel -> {
                    MaterielDTO dto = dtoMapper.toMaterielDTO(materiel);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les matériels disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirMaterielsDisponibles() {
        log.info("Demande de récupération des matériels disponibles");
        List<Materiel> materiels = materielService.obtenirMaterielsDisponibles();
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(materiels);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les ordinateurs uniquement
    @GetMapping("/ordinateurs")
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirOrdinateurs() {
        log.info("Demande de récupération des ordinateurs");
        List<Materiel> ordinateurs = materielService.obtenirOrdinateurs();
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(ordinateurs);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les vidéoprojecteurs uniquement
    @GetMapping("/videoprojecteurs")
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirVideoProjecteurs() {
        log.info("Demande de récupération des vidéoprojecteurs");
        List<Materiel> videoProjecteurs = materielService.obtenirVideoProjecteurs();
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(videoProjecteurs);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les matériels par marque
    @GetMapping("/marque/{marque}")
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirMaterielsParMarque(@PathVariable String marque) {
        log.info("Demande de récupération des matériels par marque: {}", marque);
        List<Materiel> materiels = materielService.obtenirMaterielsParMarque(marque);
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(materiels);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les matériels par état
    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirMaterielsParEtat(@PathVariable String etat) {
        log.info("Demande de récupération des matériels par état: {}", etat);
        List<Materiel> materiels = materielService.obtenirMaterielsParEtat(etat);
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(materiels);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les matériels par localisation
    @GetMapping("/localisation/{localisation}")
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirMaterielsParLocalisation(@PathVariable String localisation) {
        log.info("Demande de récupération des matériels par localisation: {}", localisation);
        List<Materiel> materiels = materielService.obtenirMaterielsParLocalisation(localisation);
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(materiels);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les matériels disponibles pour une période
    @GetMapping("/disponibles/periode")
    public ResponseEntity<List<MaterielSimpleDTO>> obtenirMaterielsDisponiblesPourPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate jour,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin) {
        log.info("Demande de récupération des matériels disponibles le {} de {} à {}", jour, heureDebut, heureFin);
        List<Materiel> materiels = materielService.obtenirMaterielsDisponiblesPourPeriode(jour, heureDebut, heureFin);
        List<MaterielSimpleDTO> dtos = dtoMapper.toMaterielSimpleDTOList(materiels);
        return ResponseEntity.ok(dtos);
    }

    // Mettre à jour un matériel
    @PutMapping("/{codeMateriel}")
    public ResponseEntity<MaterielSimpleDTO> mettreAJourMateriel(@PathVariable String codeMateriel,
                                                                 @Valid @RequestBody Materiel materiel) {
        log.info("Demande de mise à jour du matériel avec le code: {}", codeMateriel);

        try {
            Materiel materielMisAJour = materielService.mettreAJourMateriel(codeMateriel, materiel);
            MaterielSimpleDTO dto = dtoMapper.toMaterielSimpleDTO(materielMisAJour);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la mise à jour du matériel: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Modifier la disponibilité d'un matériel
    @PatchMapping("/{codeMateriel}/disponibilite")
    public ResponseEntity<MaterielSimpleDTO> modifierDisponibiliteMateriel(@PathVariable String codeMateriel,
                                                                           @RequestParam Boolean disponibilite) {
        log.info("Demande de modification de la disponibilité du matériel {} à {}", codeMateriel, disponibilite);

        try {
            Materiel materielModifie = materielService.modifierDisponibiliteMateriel(codeMateriel, disponibilite);
            MaterielSimpleDTO dto = dtoMapper.toMaterielSimpleDTO(materielModifie);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la modification de la disponibilité: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Modifier l'état d'un matériel
    @PatchMapping("/{codeMateriel}/etat")
    public ResponseEntity<MaterielSimpleDTO> modifierEtatMateriel(@PathVariable String codeMateriel,
                                                                  @RequestParam String etat) {
        log.info("Demande de modification de l'état du matériel {} à {}", codeMateriel, etat);

        try {
            Materiel materielModifie = materielService.modifierEtatMateriel(codeMateriel, etat);
            MaterielSimpleDTO dto = dtoMapper.toMaterielSimpleDTO(materielModifie);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la modification de l'état: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Déplacer un matériel
    @PatchMapping("/{codeMateriel}/localisation")
    public ResponseEntity<MaterielSimpleDTO> deplacerMateriel(@PathVariable String codeMateriel,
                                                              @RequestParam String localisation) {
        log.info("Demande de déplacement du matériel {} vers {}", codeMateriel, localisation);

        try {
            Materiel materielDeplace = materielService.deplacerMateriel(codeMateriel, localisation);
            MaterielSimpleDTO dto = dtoMapper.toMaterielSimpleDTO(materielDeplace);
            return ResponseEntity.ok(dto);
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