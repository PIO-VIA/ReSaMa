package com.example.CSI.controller;

import com.example.CSI.dto.FormationDTO;
import com.example.CSI.dto.FormationSimpleDTO;
import com.example.CSI.mapper.DTOMapper;
import com.example.CSI.model.Formation;
import com.example.CSI.service.FormationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class FormationController {

    private final FormationService formationService;
    private final DTOMapper dtoMapper;

    // Créer une nouvelle formation
    @PostMapping
    public ResponseEntity<FormationSimpleDTO> creerFormation(@Valid @RequestBody Formation formation) {
        log.info("Demande de création de formation: {}", formation.getCodeFormation());

        try {
            Formation nouvelleFormation = formationService.creerFormation(formation);
            FormationSimpleDTO dto = dtoMapper.toFormationSimpleDTO(nouvelleFormation);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de la formation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer toutes les formations
    @GetMapping
    public ResponseEntity<List<FormationSimpleDTO>> obtenirToutesLesFormations() {
        log.info("Demande de récupération de toutes les formations");
        List<Formation> formations = formationService.obtenirToutesLesFormations();
        List<FormationSimpleDTO> dtos = dtoMapper.toFormationSimpleDTOList(formations);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer une formation par ID
    @GetMapping("/{id}")
    public ResponseEntity<FormationDTO> obtenirFormationParId(@PathVariable Long id) {
        log.info("Demande de récupération de la formation avec l'ID: {}", id);

        return formationService.obtenirFormationParId(id)
                .map(formation -> {
                    FormationDTO dto = dtoMapper.toFormationDTO(formation);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer une formation par code
    @GetMapping("/code/{codeFormation}")
    public ResponseEntity<FormationDTO> obtenirFormationParCode(@PathVariable String codeFormation) {
        log.info("Demande de récupération de la formation avec le code: {}", codeFormation);

        return formationService.obtenirFormationParCode(codeFormation)
                .map(formation -> {
                    FormationDTO dto = dtoMapper.toFormationDTO(formation);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Rechercher des formations par nom
    @GetMapping("/recherche")
    public ResponseEntity<List<FormationSimpleDTO>> rechercherFormationsParNom(@RequestParam String nom) {
        log.info("Demande de recherche de formations par nom: {}", nom);
        List<Formation> formations = formationService.rechercherFormationsParNom(nom);
        List<FormationSimpleDTO> dtos = dtoMapper.toFormationSimpleDTOList(formations);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les formations par niveau
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<FormationSimpleDTO>> obtenirFormationsParNiveau(@PathVariable String niveau) {
        log.info("Demande de récupération des formations par niveau: {}", niveau);
        List<Formation> formations = formationService.obtenirFormationsParNiveau(niveau);
        List<FormationSimpleDTO> dtos = dtoMapper.toFormationSimpleDTOList(formations);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les formations d'un responsable
    @GetMapping("/responsable/{idResponsable}")
    public ResponseEntity<List<FormationSimpleDTO>> obtenirFormationsParResponsable(@PathVariable Long idResponsable) {
        log.info("Demande de récupération des formations pour le responsable: {}", idResponsable);
        List<Formation> formations = formationService.obtenirFormationsParResponsable(idResponsable);
        List<FormationSimpleDTO> dtos = dtoMapper.toFormationSimpleDTOList(formations);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer les formations d'un responsable avec détails
    @GetMapping("/responsable/{idResponsable}/details")
    public ResponseEntity<List<FormationDTO>> obtenirFormationsParResponsableAvecDetails(@PathVariable Long idResponsable) {
        log.info("Demande de récupération des formations avec détails pour le responsable: {}", idResponsable);
        List<Formation> formations = formationService.obtenirFormationsParResponsableAvecDetails(idResponsable);
        List<FormationDTO> dtos = dtoMapper.toFormationDTOList(formations);
        return ResponseEntity.ok(dtos);
    }

    // Récupérer toutes les formations avec leurs réservations
    @GetMapping("/avec-reservations")
    public ResponseEntity<List<FormationDTO>> obtenirFormationsAvecReservations() {
        log.info("Demande de récupération de toutes les formations avec leurs réservations");
        List<Formation> formations = formationService.obtenirFormationsAvecReservations();
        List<FormationDTO> dtos = dtoMapper.toFormationDTOList(formations);
        return ResponseEntity.ok(dtos);
    }

    // Mettre à jour une formation
    @PutMapping("/{id}")
    public ResponseEntity<FormationSimpleDTO> mettreAJourFormation(@PathVariable Long id,
                                                                   @Valid @RequestBody Formation formation) {
        log.info("Demande de mise à jour de la formation avec l'ID: {}", id);

        try {
            Formation formationMiseAJour = formationService.mettreAJourFormation(id, formation);
            FormationSimpleDTO dto = dtoMapper.toFormationSimpleDTO(formationMiseAJour);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la mise à jour de la formation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Changer le responsable d'une formation
    @PatchMapping("/{idFormation}/responsable/{idNouveauResponsable}")
    public ResponseEntity<FormationSimpleDTO> changerResponsable(@PathVariable Long idFormation,
                                                                 @PathVariable Long idNouveauResponsable) {
        log.info("Demande de changement du responsable de la formation {} vers l'enseignant {}",
                idFormation, idNouveauResponsable);

        try {
            Formation formationModifiee = formationService.changerResponsable(idFormation, idNouveauResponsable);
            FormationSimpleDTO dto = dtoMapper.toFormationSimpleDTO(formationModifiee);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors du changement de responsable: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Supprimer une formation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerFormation(@PathVariable Long id) {
        log.info("Demande de suppression de la formation avec l'ID: {}", id);

        try {
            formationService.supprimerFormation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la suppression de la formation: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Vérifier si une formation existe
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> formationExiste(@PathVariable Long id) {
        log.info("Vérification de l'existence de la formation avec l'ID: {}", id);
        boolean existe = formationService.formationExiste(id);
        return ResponseEntity.ok(existe);
    }

    // Vérifier si un code formation existe
    @GetMapping("/code/{codeFormation}/existe")
    public ResponseEntity<Boolean> codeFormationExiste(@PathVariable String codeFormation) {
        log.info("Vérification de l'existence du code formation: {}", codeFormation);
        boolean existe = formationService.codeFormationExiste(codeFormation);
        return ResponseEntity.ok(existe);
    }
}