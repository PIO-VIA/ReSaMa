package com.example.CSI.controller;

import com.example.CSI.model.Enseignant;
import com.example.CSI.service.EnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/enseignants")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class EnseignantController {

    private final EnseignantService enseignantService;

    // Créer un nouvel enseignant
    @PostMapping
    public ResponseEntity<Enseignant> creerEnseignant(@Valid @RequestBody Enseignant enseignant) {
        log.info("Demande de création d'enseignant: {} {}",
                enseignant.getNomEnseignant(), enseignant.getPrenomEnseignant());

        try {
            Enseignant nouvelEnseignant = enseignantService.creerEnseignant(enseignant);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelEnseignant);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de l'enseignant: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer tous les enseignants
    @GetMapping
    public ResponseEntity<List<Enseignant>> obtenirTousLesEnseignants() {
        log.info("Demande de récupération de tous les enseignants");
        List<Enseignant> enseignants = enseignantService.obtenirTousLesEnseignants();
        return ResponseEntity.ok(enseignants);
    }

    // Récupérer un enseignant par ID
    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> obtenirEnseignantParId(@PathVariable Long id) {
        log.info("Demande de récupération de l'enseignant avec l'ID: {}", id);

        return enseignantService.obtenirEnseignantParId(id)
                .map(enseignant -> ResponseEntity.ok(enseignant))
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer un enseignant par email
    @GetMapping("/email/{email}")
    public ResponseEntity<Enseignant> obtenirEnseignantParEmail(@PathVariable String email) {
        log.info("Demande de récupération de l'enseignant avec l'email: {}", email);

        return enseignantService.obtenirEnseignantParEmail(email)
                .map(enseignant -> ResponseEntity.ok(enseignant))
                .orElse(ResponseEntity.notFound().build());
    }

    // Rechercher des enseignants par nom
    @GetMapping("/recherche")
    public ResponseEntity<List<Enseignant>> rechercherEnseignantsParNom(@RequestParam String nom) {
        log.info("Demande de recherche d'enseignants par nom: {}", nom);
        List<Enseignant> enseignants = enseignantService.rechercherEnseignantsParNom(nom);
        return ResponseEntity.ok(enseignants);
    }

    // Récupérer les enseignants par spécialité
    @GetMapping("/specialite/{specialite}")
    public ResponseEntity<List<Enseignant>> obtenirEnseignantsParSpecialite(@PathVariable String specialite) {
        log.info("Demande de récupération des enseignants par spécialité: {}", specialite);
        List<Enseignant> enseignants = enseignantService.obtenirEnseignantsParSpecialite(specialite);
        return ResponseEntity.ok(enseignants);
    }

    // Récupérer les enseignants responsables
    @GetMapping("/responsables")
    public ResponseEntity<List<Enseignant>> obtenirEnseignantsResponsables() {
        log.info("Demande de récupération des enseignants responsables");
        List<Enseignant> enseignants = enseignantService.obtenirEnseignantsResponsables();
        return ResponseEntity.ok(enseignants);
    }

    // Récupérer les enseignants avec leurs réservations pour une période
    @GetMapping("/avec-reservations")
    public ResponseEntity<List<Enseignant>> obtenirEnseignantsAvecReservations(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        log.info("Demande de récupération des enseignants avec réservations du {} au {}", dateDebut, dateFin);
        List<Enseignant> enseignants = enseignantService.obtenirEnseignantsAvecReservations(dateDebut, dateFin);
        return ResponseEntity.ok(enseignants);
    }

    // Mettre à jour un enseignant
    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> mettreAJourEnseignant(@PathVariable Long id,
                                                            @Valid @RequestBody Enseignant enseignant) {
        log.info("Demande de mise à jour de l'enseignant avec l'ID: {}", id);

        try {
            Enseignant enseignantMisAJour = enseignantService.mettreAJourEnseignant(id, enseignant);
            return ResponseEntity.ok(enseignantMisAJour);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la mise à jour de l'enseignant: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Supprimer un enseignant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long id) {
        log.info("Demande de suppression de l'enseignant avec l'ID: {}", id);

        try {
            enseignantService.supprimerEnseignant(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la suppression de l'enseignant: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Vérifier si un enseignant existe
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> enseignantExiste(@PathVariable Long id) {
        log.info("Vérification de l'existence de l'enseignant avec l'ID: {}", id);
        boolean existe = enseignantService.enseignantExiste(id);
        return ResponseEntity.ok(existe);
    }
}