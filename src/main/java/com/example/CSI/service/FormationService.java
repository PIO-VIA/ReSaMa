package com.example.CSI.service;


import com.example.CSI.model.Formation;
import com.example.CSI.repository.EnseignantRepository;
import com.example.CSI.repository.FormationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FormationService {

    private final FormationRepository formationRepository;
    private final EnseignantRepository enseignantRepository;

    // Créer une nouvelle formation
    public Formation creerFormation(Formation formation) {
        log.info("Création d'une nouvelle formation: {}", formation.getCodeFormation());

        // Vérifier si le code formation existe déjà
        if (formationRepository.existsByCodeFormation(formation.getCodeFormation())) {
            throw new IllegalArgumentException("Une formation avec ce code existe déjà");
        }

        // Vérifier que le responsable existe
        if (!enseignantRepository.existsById(formation.getResponsable().getIdEnseignant())) {
            throw new IllegalArgumentException("Enseignant responsable non trouvé");
        }

        return formationRepository.save(formation);
    }

    // Récupérer toutes les formations
    @Transactional(readOnly = true)
    public List<Formation> obtenirToutesLesFormations() {
        log.info("Récupération de toutes les formations");
        return formationRepository.findAll();
    }

    // Récupérer une formation par ID
    @Transactional(readOnly = true)
    public Optional<Formation> obtenirFormationParId(Long id) {
        log.info("Récupération de la formation avec l'ID: {}", id);
        return formationRepository.findById(id);
    }

    // Récupérer une formation par code
    @Transactional(readOnly = true)
    public Optional<Formation> obtenirFormationParCode(String codeFormation) {
        log.info("Récupération de la formation avec le code: {}", codeFormation);
        return formationRepository.findByCodeFormation(codeFormation);
    }

    // Rechercher des formations par nom
    @Transactional(readOnly = true)
    public List<Formation> rechercherFormationsParNom(String nomFormation) {
        log.info("Recherche de formations par nom: {}", nomFormation);
        return formationRepository.findByNomFormationContainingIgnoreCase(nomFormation);
    }

    // Récupérer les formations par niveau
    @Transactional(readOnly = true)
    public List<Formation> obtenirFormationsParNiveau(String niveau) {
        log.info("Récupération des formations par niveau: {}", niveau);
        return formationRepository.findByNiveau(niveau);
    }

    // Récupérer les formations d'un responsable
    @Transactional(readOnly = true)
    public List<Formation> obtenirFormationsParResponsable(Long idResponsable) {
        log.info("Récupération des formations pour le responsable: {}", idResponsable);
        return formationRepository.findByResponsableIdEnseignant(idResponsable);
    }

    // Récupérer les formations d'un responsable avec détails
    @Transactional(readOnly = true)
    public List<Formation> obtenirFormationsParResponsableAvecDetails(Long idResponsable) {
        log.info("Récupération des formations avec détails pour le responsable: {}", idResponsable);
        return formationRepository.findByResponsableWithDetails(idResponsable);
    }

    // Récupérer toutes les formations avec leurs réservations
    @Transactional(readOnly = true)
    public List<Formation> obtenirFormationsAvecReservations() {
        log.info("Récupération de toutes les formations avec leurs réservations");
        return formationRepository.findAllWithReservations();
    }

    // Mettre à jour une formation
    public Formation mettreAJourFormation(Long id, Formation formationModifiee) {
        log.info("Mise à jour de la formation avec l'ID: {}", id);

        return formationRepository.findById(id)
                .map(formation -> {
                    // Vérifier le code formation si modifié
                    if (!formationModifiee.getCodeFormation().equals(formation.getCodeFormation()) &&
                            formationRepository.existsByCodeFormation(formationModifiee.getCodeFormation())) {
                        throw new IllegalArgumentException("Une formation avec ce code existe déjà");
                    }

                    // Vérifier que le responsable existe
                    if (!enseignantRepository.existsById(formationModifiee.getResponsable().getIdEnseignant())) {
                        throw new IllegalArgumentException("Enseignant responsable non trouvé");
                    }

                    formation.setCodeFormation(formationModifiee.getCodeFormation());
                    formation.setNomFormation(formationModifiee.getNomFormation());
                    formation.setDescription(formationModifiee.getDescription());
                    formation.setNiveau(formationModifiee.getNiveau());
                    formation.setDureeHeures(formationModifiee.getDureeHeures());
                    formation.setResponsable(formationModifiee.getResponsable());

                    return formationRepository.save(formation);
                })
                .orElseThrow(() -> new IllegalArgumentException("Formation non trouvée avec l'ID: " + id));
    }

    // Changer le responsable d'une formation
    public Formation changerResponsable(Long idFormation, Long idNouveauResponsable) {
        log.info("Changement du responsable de la formation {} vers l'enseignant {}", idFormation, idNouveauResponsable);

        return formationRepository.findById(idFormation)
                .map(formation -> {
                    return enseignantRepository.findById(idNouveauResponsable)
                            .map(enseignant -> {
                                formation.setResponsable(enseignant);
                                return formationRepository.save(formation);
                            })
                            .orElseThrow(() -> new IllegalArgumentException("Enseignant non trouvé avec l'ID: " + idNouveauResponsable));
                })
                .orElseThrow(() -> new IllegalArgumentException("Formation non trouvée avec l'ID: " + idFormation));
    }

    // Supprimer une formation
    public void supprimerFormation(Long id) {
        log.info("Suppression de la formation avec l'ID: {}", id);

        if (!formationRepository.existsById(id)) {
            throw new IllegalArgumentException("Formation non trouvée avec l'ID: " + id);
        }

        formationRepository.deleteById(id);
    }

    // Vérifier si une formation existe
    @Transactional(readOnly = true)
    public boolean formationExiste(Long id) {
        return formationRepository.existsById(id);
    }

    // Vérifier si un code formation existe
    @Transactional(readOnly = true)
    public boolean codeFormationExiste(String codeFormation) {
        return formationRepository.existsByCodeFormation(codeFormation);
    }
}