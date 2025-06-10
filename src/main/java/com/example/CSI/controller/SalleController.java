package com.example.CSI.controller;


import com.example.CSI.model.Salle;
import com.example.CSI.service.SalleService;
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
@RequestMapping("/salles")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SalleController {

    private final SalleService salleService;

    // Créer une nouvelle salle
    @PostMapping
    public ResponseEntity<Salle> creerSalle(@Valid @RequestBody Salle salle) {
        log.info("Demande de création de salle: {}", salle.getCodeSalle());

        try {
            Salle nouvelleSalle = salleService.creerSalle(salle);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleSalle);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de la salle: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer toutes les salles
    @GetMapping
    public ResponseEntity<List<Salle>> obtenirToutesLesSalles() {
        log.info("Demande de récupération de toutes les salles");
        List<Salle> salles = salleService.obtenirToutesLesSalles();
        return ResponseEntity.ok(salles);
    }

    // Récupérer une salle par code
    @GetMapping("/{codeSalle}")
    public ResponseEntity<Salle> obtenirSalleParCode(@PathVariable String codeSalle) {
        log.info("Demande de récupération de la salle avec le code: {}", codeSalle);

        return salleService.obtenirSalleParCode(codeSalle)
                .map(salle -> ResponseEntity.ok(salle))
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les salles disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Salle>> obtenirSallesDisponibles() {
        log.info("Demande de récupération des salles disponibles");
        List<Salle> salles = salleService.obtenirSallesDisponibles();
        return ResponseEntity.ok(salles);
    }

    // Récupérer les salles par type
    @GetMapping("/type/{typeSalle}")
    public ResponseEntity<List<Salle>> obtenirSallesParType(@PathVariable String typeSalle) {
        log.info("Demande de récupération des salles de type: {}", typeSalle);
        List<Salle> salles = salleService.obtenirSallesParType(typeSalle);
        return ResponseEntity.ok(salles);
    }

    // Récupérer les salles par bâtiment
    @GetMapping("/batiment/{batiment}")
    public ResponseEntity<List<Salle>> obtenirSallesParBatiment(@PathVariable String batiment) {
        log.info("Demande de récupération des salles du bâtiment: {}", batiment);
        List<Salle> salles = salleService.obtenirSallesParBatiment(batiment);
        return ResponseEntity.ok(salles);
    }

    // Récupérer les salles avec capacité minimale
    @GetMapping("/capacite/{capaciteMin}")
    public ResponseEntity<List<Salle>> obtenirSallesAvecCapaciteMin(@PathVariable Integer capaciteMin) {
        log.info("Demande de récupération des salles avec capacité >= {}", capaciteMin);
        List<Salle> salles = salleService.obtenirSallesAvecCapaciteMin(capaciteMin);
        return ResponseEntity.ok(salles);
    }

    // Rechercher des salles par nom
    @GetMapping("/recherche")
    public ResponseEntity<List<Salle>> rechercherSallesParNom(@RequestParam String nom) {
        log.info("Demande de recherche de salles par nom: {}", nom);
        List<Salle> salles = salleService.rechercherSallesParNom(nom);
        return ResponseEntity.ok(salles);
    }

    // Récupérer les salles disponibles pour une période
    @GetMapping("/disponibles/periode")
    public ResponseEntity<List<Salle>> obtenirSallesDisponiblesPourPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate jour,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin) {
        log.info("Demande de récupération des salles disponibles le {} de {} à {}", jour, heureDebut, heureFin);
        List<Salle> salles = salleService.obtenirSallesDisponiblesPourPeriode(jour, heureDebut, heureFin);
        return ResponseEntity.ok(salles);
    }

    // Récupérer les salles disponibles avec capacité suffisante
    @GetMapping("/disponibles/avec-capacite")
    public ResponseEntity<List<Salle>> obtenirSallesDisponiblesAvecCapacite(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate jour,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin,
            @RequestParam Integer capaciteMin) {
        log.info("Demande de récupération des salles disponibles le {} de {} à {} avec capacité >= {}",
                jour, heureDebut, heureFin, capaciteMin);
        List<Salle> salles = salleService.obtenirSallesDisponiblesAvecCapacite(jour, heureDebut, heureFin, capaciteMin);
        return ResponseEntity.ok(salles);
    }

    // Mettre à jour une salle
    @PutMapping("/{codeSalle}")
    public ResponseEntity<Salle> mettreAJourSalle(@PathVariable String codeSalle,
                                                  @Valid @RequestBody Salle salle) {
        log.info("Demande de mise à jour de la salle avec le code: {}", codeSalle);

        try {
            Salle salleMiseAJour = salleService.mettreAJourSalle(codeSalle, salle);
            return ResponseEntity.ok(salleMiseAJour);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la mise à jour de la salle: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Modifier la disponibilité d'une salle
    @PatchMapping("/{codeSalle}/disponibilite")
    public ResponseEntity<Salle> modifierDisponibiliteSalle(@PathVariable String codeSalle,
                                                            @RequestParam Boolean disponibilite) {
        log.info("Demande de modification de la disponibilité de la salle {} à {}", codeSalle, disponibilite);

        try {
            Salle salleModifiee = salleService.modifierDisponibiliteSalle(codeSalle, disponibilite);
            return ResponseEntity.ok(salleModifiee);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la modification de la disponibilité: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une salle
    @DeleteMapping("/{codeSalle}")
    public ResponseEntity<Void> supprimerSalle(@PathVariable String codeSalle) {
        log.info("Demande de suppression de la salle avec le code: {}", codeSalle);

        try {
            salleService.supprimerSalle(codeSalle);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la suppression de la salle: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Vérifier si une salle existe
    @GetMapping("/{codeSalle}/existe")
    public ResponseEntity<Boolean> salleExiste(@PathVariable String codeSalle) {
        log.info("Vérification de l'existence de la salle avec le code: {}", codeSalle);
        boolean existe = salleService.salleExiste(codeSalle);
        return ResponseEntity.ok(existe);
    }
}