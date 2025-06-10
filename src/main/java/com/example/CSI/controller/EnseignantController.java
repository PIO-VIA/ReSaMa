package com.example.CSI.controller;

import com.example.CSI.dto.EnseignantDTO;
import com.example.CSI.dto.EnseignantSimpleDTO;
import com.example.CSI.mapper.DTOMapper;
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
    private final DTOMapper dtoMapper;

    // Créer un nouvel enseignant
    @PostMapping
    public ResponseEntity<EnseignantSimpleDTO> creerEnseignant(@Valid @RequestBody Enseignant enseignant) {
        log.info("Demande de création d'enseignant: {} {}",
                enseignant.getNomEnseignant(), enseignant.getPrenomEnseignant());

        try {
            Enseignant nouvelEnseignant = enseignantService.creerEnseignant(enseignant);
            EnseignantSimpleDTO dto = dtoMapper.toEnseignantSimpleDTO(nouvelEnseignant);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de l'enseignant: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer tous les enseignants
    @GetMapping
    public ResponseEntity<List<EnseignantSimpleDTO>> obtenirTousLesEnseignants() {
        log.info("Demande de récupération de tous les enseignants");
        List<Enseignant> enseignants = enseignantService.obtenirTousLesEnseignants();
        List<EnseignantSimpleDTO> dtos = dtoMapper.toEnseignantSimpleDTOList(enseignants);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer un enseignant par ID
    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> obtenirEnseignantParId(@PathVariable Long id) {
        log.info("Demande de récupération de l'enseignant avec l'ID: {}", id);

        return enseignantService.obtenirEnseignantParId(id)
                .map(enseignant -> {
                    EnseignantDTO dto = dtoMapper.toEnseignantDTO(enseignant);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer un enseignant par email
    @GetMapping("/email/{email}")
    public ResponseEntity<EnseignantDTO> obtenirEnseignantParEmail(@PathVariable String email) {
        log.info("Demande de récupération de l'enseignant avec l'email: {}", email);

        return enseignantService.obtenirEnseignantParEmail(email)
                .map(enseignant -> {
                    EnseignantDTO dto = dtoMapper.toEnseignantDTO(enseignant);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Rechercher des enseignants par nom
    @GetMapping("/recherche")
    public ResponseEntity<List<EnseignantSimpleDTO>> rechercherEnseignantsParNom(@RequestParam String nom) {
        log.info("Demande de recherche d'enseignants par nom: {}", nom);
        List<Enseignant> enseignants = enseignantService.rechercherEnseignantsParNom(nom);
        List<EnseignantSimpleDTO> dtos = dtoMapper.toEnseignantSimpleDTOList(enseignants);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les enseignants par spécialité
    @GetMapping("/specialite/{specialite}")
    public ResponseEntity<List<EnseignantSimpleDTO>> obtenirEnseignantsParSpecialite(@PathVariable String specialite) {
        log.info("Demande de récupération des enseignants par spécialité: {}", specialite);
        List<Enseignant> enseignants = enseignantService.obtenirEnseignantsParSpecialite(specialite);
        List<EnseignantSimpleDTO> dtos = dtoMapper.toEnseignantSimpleDTOList(enseignants);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les enseignants responsables
    @GetMapping("/responsables")
    public ResponseEntity<List<EnseignantSimpleDTO>> obtenirEnseignantsResponsables() {
        log.info("Demande de récupération des enseignants responsables");
        List<Enseignant> enseignants = enseignantService.obtenirEnseignantsResponsables();
        List<EnseignantSimpleDTO> dtos = dtoMapper.toEnseignantSimpleDTOList(enseignants);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les enseignants avec leurs réservations pour une période
    @GetMapping("/avec-reservations")
    public ResponseEntity<List<EnseignantDTO>> obtenirEnseignantsAvecReservations(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        log.info("Demande de récupération des enseignants avec réservations du {} au {}", dateDebut, dateFin);
        List<Enseignant> enseignants = enseignantService.obtenirEnseignantsAvecReservations(dateDebut, dateFin);
        List<EnseignantDTO> dtos = dtoMapper.toEnseignantDTOList(enseignants);
        return ResponseEntity.ok(dtos);
    }

    // Mettre à jour un enseignant
    @PutMapping("/{id}")
    public ResponseEntity<EnseignantSimpleDTO> mettreAJourEnseignant(@PathVariable Long id,
                                                                     @Valid @RequestBody Enseignant enseignant) {
        log.info("Demande de mise à jour de l'enseignant avec l'ID: {}", id);

        try {
            Enseignant enseignantMisAJour = enseignantService.mettreAJourEnseignant(id, enseignant);
            EnseignantSimpleDTO dto = dtoMapper.toEnseignantSimpleDTO(enseignantMisAJour);
            return ResponseEntity.ok(dto);
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