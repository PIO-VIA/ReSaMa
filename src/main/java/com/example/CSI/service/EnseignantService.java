package com.example.CSI.service;


import com.example.CSI.model.Enseignant;
import com.example.CSI.repository.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;

    // Créer un nouvel enseignant
    public Enseignant creerEnseignant(Enseignant enseignant) {
        log.info("Création d'un nouvel enseignant: {} {}",
                enseignant.getNomEnseignant(), enseignant.getPrenomEnseignant());

        // Vérifier si l'email existe déjà
        if (enseignant.getEmail() != null && enseignantRepository.existsByEmail(enseignant.getEmail())) {
            throw new IllegalArgumentException("Un enseignant avec cet email existe déjà");
        }

        return enseignantRepository.save(enseignant);
    }

    // Récupérer tous les enseignants
    @Transactional(readOnly = true)
    public List<Enseignant> obtenirTousLesEnseignants() {
        log.info("Récupération de tous les enseignants");
        return enseignantRepository.findAll();
    }

    // Récupérer un enseignant par ID
    @Transactional(readOnly = true)
    public Optional<Enseignant> obtenirEnseignantParId(Long id) {
        log.info("Récupération de l'enseignant avec l'ID: {}", id);
        return enseignantRepository.findById(id);
    }

    // Récupérer un enseignant par email
    @Transactional(readOnly = true)
    public Optional<Enseignant> obtenirEnseignantParEmail(String email) {
        log.info("Récupération de l'enseignant avec l'email: {}", email);
        return enseignantRepository.findByEmail(email);
    }

    // Rechercher des enseignants par nom
    @Transactional(readOnly = true)
    public List<Enseignant> rechercherEnseignantsParNom(String nom) {
        log.info("Recherche d'enseignants par nom: {}", nom);
        return enseignantRepository.findByNomEnseignantContainingIgnoreCase(nom);
    }

    // Récupérer les enseignants par spécialité
    @Transactional(readOnly = true)
    public List<Enseignant> obtenirEnseignantsParSpecialite(String specialite) {
        log.info("Récupération des enseignants par spécialité: {}", specialite);
        return enseignantRepository.findBySpecialite(specialite);
    }

    // Récupérer les enseignants responsables
    @Transactional(readOnly = true)
    public List<Enseignant> obtenirEnseignantsResponsables() {
        log.info("Récupération des enseignants responsables");
        return enseignantRepository.findEnseignantsResponsables();
    }

    // Mettre à jour un enseignant
    public Enseignant mettreAJourEnseignant(Long id, Enseignant enseignantModifie) {
        log.info("Mise à jour de l'enseignant avec l'ID: {}", id);

        return enseignantRepository.findById(id)
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
                .orElseThrow(() -> new IllegalArgumentException("Enseignant non trouvé avec l'ID: " + id));
    }

    // Supprimer un enseignant
    public void supprimerEnseignant(Long id) {
        log.info("Suppression de l'enseignant avec l'ID: {}", id);

        if (!enseignantRepository.existsById(id)) {
            throw new IllegalArgumentException("Enseignant non trouvé avec l'ID: " + id);
        }

        enseignantRepository.deleteById(id);
    }

    // Récupérer les enseignants avec leurs réservations pour une période
    @Transactional(readOnly = true)
    public List<Enseignant> obtenirEnseignantsAvecReservations(LocalDate dateDebut, LocalDate dateFin) {
        log.info("Récupération des enseignants avec réservations entre {} et {}", dateDebut, dateFin);
        return enseignantRepository.findEnseignantsWithReservationsBetween(dateDebut, dateFin);
    }

    // Vérifier si un enseignant existe
    @Transactional(readOnly = true)
    public boolean enseignantExiste(Long id) {
        return enseignantRepository.existsById(id);
    }
}