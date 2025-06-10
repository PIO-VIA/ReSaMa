package com.example.CSI.controller;


import com.example.CSI.model.Enseignant;
import com.example.CSI.model.Formation;
import com.example.CSI.service.ResponsableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsables")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ResponsableController {

    private final ResponsableService responsableService;

    // Vérifier si un enseignant est responsable
    @GetMapping("/{idEnseignant}/est-responsable")
    public ResponseEntity<Boolean> estResponsableDeFormation(@PathVariable Long idEnseignant) {
        log.info("Vérification si l'enseignant {} est responsable", idEnseignant);
        boolean estResponsable = responsableService.estResponsableDeFormation(idEnseignant);
        return ResponseEntity.ok(estResponsable);
    }

    // Récupérer les informations du responsable
    @GetMapping("/{idResponsable}/profil")
    public ResponseEntity<Enseignant> obtenirProfilResponsable(@PathVariable Long idResponsable) {
        log.info("Récupération du profil du responsable: {}", idResponsable);

        return responsableService.obtenirInformationsResponsable(idResponsable)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les formations dont l'enseignant est responsable
    @GetMapping("/{idResponsable}/formations")
    public ResponseEntity<List<Formation>> obtenirFormationsResponsables(@PathVariable Long idResponsable) {
        log.info("Récupération des formations pour le responsable: {}", idResponsable);

        try {
            List<Formation> formations = responsableService.obtenirFormationsResponsables(idResponsable);
            return ResponseEntity.ok(formations);
        } catch (IllegalArgumentException e) {
            log.error("Erreur: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // === GESTION DES ENSEIGNANTS PAR LES RESPONSABLES ===

    // Créer un nouvel enseignant (réservé aux responsables)
    @PostMapping("/{idResponsable}/enseignants")
    public ResponseEntity<Enseignant> creerEnseignant(@PathVariable Long idResponsable,
                                                      @Valid @RequestBody Enseignant enseignant) {
        log.info("Création d'enseignant par le responsable {}: {} {}",
                idResponsable, enseignant.getNomEnseignant(), enseignant.getPrenomEnseignant());

        try {
            Enseignant nouvelEnseignant = responsableService.creerEnseignantParResponsable(idResponsable, enseignant);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelEnseignant);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de l'enseignant: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer tous les enseignants (pour gestion par responsable)
    @GetMapping("/{idResponsable}/enseignants")
    public ResponseEntity<List<Enseignant>> obtenirTousLesEnseignants(@PathVariable Long idResponsable) {
        log.info("Récupération de tous les enseignants par le responsable: {}", idResponsable);

        try {
            List<Enseignant> enseignants = responsableService.obtenirTousLesEnseignantsParResponsable(idResponsable);
            return ResponseEntity.ok(enseignants);
        } catch (IllegalArgumentException e) {
            log.error("Accès refusé: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Récupérer les enseignants non-responsables
    @GetMapping("/{idResponsable}/enseignants/non-responsables")
    public ResponseEntity<List<Enseignant>> obtenirEnseignantsNonResponsables(@PathVariable Long idResponsable) {
        log.info("Récupération des enseignants non-responsables par: {}", idResponsable);

        try {
            List<Enseignant> enseignants = responsableService.obtenirEnseignantsNonResponsables(idResponsable);
            return ResponseEntity.ok(enseignants);
        } catch (IllegalArgumentException e) {
            log.error("Accès refusé: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Mettre à jour un enseignant (réservé aux responsables)
    @PutMapping("/{idResponsable}/enseignants/{idEnseignant}")
    public ResponseEntity<Enseignant> mettreAJourEnseignant(@PathVariable Long idResponsable,
                                                            @PathVariable Long idEnseignant,
                                                            @Valid @RequestBody Enseignant enseignant) {
        log.info("Mise à jour de l'enseignant {} par le responsable {}", idEnseignant, idResponsable);

        try {
            Enseignant enseignantMisAJour = responsableService.mettreAJourEnseignantParResponsable(
                    idResponsable, idEnseignant, enseignant);
            return ResponseEntity.ok(enseignantMisAJour);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la mise à jour: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Supprimer un enseignant (réservé aux responsables)
    @DeleteMapping("/{idResponsable}/enseignants/{idEnseignant}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long idResponsable,
                                                    @PathVariable Long idEnseignant) {
        log.info("Suppression de l'enseignant {} par le responsable {}", idEnseignant, idResponsable);

        try {
            responsableService.supprimerEnseignantParResponsable(idResponsable, idEnseignant);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la suppression: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Assigner un enseignant à une formation
    @PostMapping("/{idResponsable}/formations/{idFormation}/enseignants/{idEnseignant}")
    public ResponseEntity<Void> assignerEnseignantAFormation(@PathVariable Long idResponsable,
                                                             @PathVariable Long idFormation,
                                                             @PathVariable Long idEnseignant) {
        log.info("Assignation de l'enseignant {} à la formation {} par le responsable {}",
                idEnseignant, idFormation, idResponsable);

        try {
            responsableService.assignerEnseignantAFormation(idResponsable, idEnseignant, idFormation);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de l'assignation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // === ENDPOINTS INFORMATIFS ===

    // Obtenir des statistiques pour le tableau de bord du responsable
    @GetMapping("/{idResponsable}/tableau-bord")
    public ResponseEntity<TableauBordResponsable> obtenirTableauBord(@PathVariable Long idResponsable) {
        log.info("Récupération du tableau de bord pour le responsable: {}", idResponsable);

        try {
            List<Formation> formations = responsableService.obtenirFormationsResponsables(idResponsable);
            List<Enseignant> enseignants = responsableService.obtenirTousLesEnseignantsParResponsable(idResponsable);
            List<Enseignant> nonResponsables = responsableService.obtenirEnseignantsNonResponsables(idResponsable);

            TableauBordResponsable tableauBord = new TableauBordResponsable(
                    formations.size(),
                    enseignants.size(),
                    nonResponsables.size(),
                    formations,
                    enseignants
            );

            return ResponseEntity.ok(tableauBord);
        } catch (IllegalArgumentException e) {
            log.error("Accès refusé au tableau de bord: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Classe pour le tableau de bord
    public static class TableauBordResponsable {
        private int nombreFormations;
        private int nombreEnseignantsTotal;
        private int nombreEnseignantsNonResponsables;
        private List<Formation> formations;
        private List<Enseignant> enseignants;

        public TableauBordResponsable(int nombreFormations, int nombreEnseignantsTotal,
                                      int nombreEnseignantsNonResponsables, List<Formation> formations,
                                      List<Enseignant> enseignants) {
            this.nombreFormations = nombreFormations;
            this.nombreEnseignantsTotal = nombreEnseignantsTotal;
            this.nombreEnseignantsNonResponsables = nombreEnseignantsNonResponsables;
            this.formations = formations;
            this.enseignants = enseignants;
        }

        // Getters
        public int getNombreFormations() { return nombreFormations; }
        public int getNombreEnseignantsTotal() { return nombreEnseignantsTotal; }
        public int getNombreEnseignantsNonResponsables() { return nombreEnseignantsNonResponsables; }
        public List<Formation> getFormations() { return formations; }
        public List<Enseignant> getEnseignants() { return enseignants; }
    }
}