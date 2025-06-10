package com.example.CSI.service;


import com.example.CSI.model.Enseignant;
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
public class ResponsableService {

    private final EnseignantRepository enseignantRepository;
    private final FormationRepository formationRepository;

    // Vérifier si un enseignant est responsable de formation
    public boolean estResponsableDeFormation(Long idEnseignant) {
        log.info("Vérification si l'enseignant {} est responsable de formation", idEnseignant);
        List<Formation> formations = formationRepository.findByResponsableIdEnseignant(idEnseignant);
        return !formations.isEmpty();
    }

    // Récupérer les formations dont l'enseignant est responsable
    @Transactional(readOnly = true)
    public List<Formation> obtenirFormationsResponsables(Long idResponsable) {
        log.info("Récupération des formations pour le responsable: {}", idResponsable);
        return formationRepository.findByResponsableIdEnseignant(idResponsable);
    }

    // Créer un nouvel enseignant (réservé aux responsables)
    public Enseignant creerEnseignantParResponsable(Long idResponsable, Enseignant enseignant) {
        log.info("Création d'enseignant par le responsable {}: {} {}",
                idResponsable, enseignant.getNomEnseignant(), enseignant.getPrenomEnseignant());

        // Vérifier que l'utilisateur est bien responsable
        if (!estResponsableDeFormation(idResponsable)) {
            throw new IllegalArgumentException("Seuls les responsables de formation peuvent créer des enseignants");
        }

        // Vérifier si l'email existe déjà
        if (enseignant.getEmail() != null && enseignantRepository.existsByEmail(enseignant.getEmail())) {
            throw new IllegalArgumentException("Un enseignant avec cet email existe déjà");
        }

        return enseignantRepository.save(enseignant);
    }

    // Mettre à jour un enseignant (réservé aux responsables)
    public Enseignant mettreAJourEnseignantParResponsable(Long idResponsable, Long idEnseignant, Enseignant enseignantModifie) {
        log.info("Mise à jour de l'enseignant {} par le responsable {}", idEnseignant, idResponsable);

        // Vérifier que l'utilisateur est bien responsable
        if (!estResponsableDeFormation(idResponsable)) {
            throw new IllegalArgumentException("Seuls les responsables de formation peuvent modifier des enseignants");
        }

        return enseignantRepository.findById(idEnseignant)
                .map(enseignant -> {
                    // Vérifier l'email si modifié
                    if (enseignantModifie.getEmail() != null &&
                            !enseignantModifie.getEmail().equals(enseignant.getEmail()) &&
                            enseignantRepository.existsByEmail(enseignantModifie.getEmail())) {
                        throw new IllegalArgumentException("Un enseignant avec cet email existe déjà");
                    }

                    enseignant.setNomEnseignant(enseignantModifie.getNomEnseignant());
                    enseignant.setPrenomEnseignant(enseignantModifie.getPrenomEnseignant());
                    enseignant.setEmail(enseignantModifie.getEmail());
                    enseignant.setTelephone(enseignantModifie.getTelephone());
                    enseignant.setSpecialite(enseignantModifie.getSpecialite());

                    return enseignantRepository.save(enseignant);
                })
                .orElseThrow(() -> new IllegalArgumentException("Enseignant non trouvé avec l'ID: " + idEnseignant));
    }

    // Supprimer un enseignant (réservé aux responsables)
    public void supprimerEnseignantParResponsable(Long idResponsable, Long idEnseignant) {
        log.info("Suppression de l'enseignant {} par le responsable {}", idEnseignant, idResponsable);

        // Vérifier que l'utilisateur est bien responsable
        if (!estResponsableDeFormation(idResponsable)) {
            throw new IllegalArgumentException("Seuls les responsables de formation peuvent supprimer des enseignants");
        }

        // Vérifier que l'enseignant à supprimer n'est pas lui-même responsable
        if (estResponsableDeFormation(idEnseignant)) {
            throw new IllegalArgumentException("Impossible de supprimer un enseignant responsable de formation");
        }

        if (!enseignantRepository.existsById(idEnseignant)) {
            throw new IllegalArgumentException("Enseignant non trouvé avec l'ID: " + idEnseignant);
        }

        enseignantRepository.deleteById(idEnseignant);
    }

    // Récupérer tous les enseignants (accessible aux responsables pour gestion)
    @Transactional(readOnly = true)
    public List<Enseignant> obtenirTousLesEnseignantsParResponsable(Long idResponsable) {
        log.info("Récupération de tous les enseignants par le responsable: {}", idResponsable);

        // Vérifier que l'utilisateur est bien responsable
        if (!estResponsableDeFormation(idResponsable)) {
            throw new IllegalArgumentException("Seuls les responsables de formation peuvent accéder à cette fonction");
        }

        return enseignantRepository.findAll();
    }

    // Récupérer les enseignants non-responsables (pour éviter les conflits)
    @Transactional(readOnly = true)
    public List<Enseignant> obtenirEnseignantsNonResponsables(Long idResponsable) {
        log.info("Récupération des enseignants non-responsables par: {}", idResponsable);

        // Vérifier que l'utilisateur est bien responsable
        if (!estResponsableDeFormation(idResponsable)) {
            throw new IllegalArgumentException("Seuls les responsables de formation peuvent accéder à cette fonction");
        }

        List<Enseignant> tousEnseignants = enseignantRepository.findAll();
        List<Enseignant> responsables = enseignantRepository.findEnseignantsResponsables();

        // Filtrer pour ne garder que les non-responsables
        return tousEnseignants.stream()
                .filter(enseignant -> responsables.stream()
                        .noneMatch(resp -> resp.getIdEnseignant().equals(enseignant.getIdEnseignant())))
                .toList();
    }

    // Assigner un enseignant à une formation (si le responsable le souhaite)
    public void assignerEnseignantAFormation(Long idResponsable, Long idEnseignant, Long idFormation) {
        log.info("Assignation de l'enseignant {} à la formation {} par le responsable {}",
                idEnseignant, idFormation, idResponsable);

        // Vérifier que l'utilisateur est bien responsable de cette formation
        Optional<Formation> formation = formationRepository.findById(idFormation);
        if (formation.isEmpty()) {
            throw new IllegalArgumentException("Formation non trouvée");
        }

        if (!formation.get().getResponsable().getIdEnseignant().equals(idResponsable)) {
            throw new IllegalArgumentException("Vous n'êtes pas responsable de cette formation");
        }

        // Vérifier que l'enseignant existe
        if (!enseignantRepository.existsById(idEnseignant)) {
            throw new IllegalArgumentException("Enseignant non trouvé");
        }

        // Cette méthode peut être étendue pour gérer des assignations spécifiques
        // Par exemple, créer une table de liaison enseignant-formation pour les cours
        log.info("Assignation effectuée avec succès");
    }

    // Obtenir les informations du responsable
    @Transactional(readOnly = true)
    public Optional<Enseignant> obtenirInformationsResponsable(Long idResponsable) {
        log.info("Récupération des informations du responsable: {}", idResponsable);

        Optional<Enseignant> enseignant = enseignantRepository.findById(idResponsable);
        if (enseignant.isPresent() && estResponsableDeFormation(idResponsable)) {
            return enseignant;
        }

        return Optional.empty();
    }
}