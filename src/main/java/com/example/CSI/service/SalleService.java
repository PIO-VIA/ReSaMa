package com.example.CSI.service;

import com.example.CSI.model.Salle;
import com.example.CSI.repository.SalleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SalleService {

    private final SalleRepository salleRepository;

    // Créer une nouvelle salle
    public Salle creerSalle(Salle salle) {
        log.info("Création d'une nouvelle salle: {}", salle.getCodeSalle());

        if (salleRepository.existsById(salle.getCodeSalle())) {
            throw new IllegalArgumentException("Une salle avec ce code existe déjà");
        }

        return salleRepository.save(salle);
    }

    // Récupérer toutes les salles
    @Transactional(readOnly = true)
    public List<Salle> obtenirToutesLesSalles() {
        log.info("Récupération de toutes les salles");
        return salleRepository.findAll();
    }

    // Récupérer une salle par code
    @Transactional(readOnly = true)
    public Optional<Salle> obtenirSalleParCode(String codeSalle) {
        log.info("Récupération de la salle avec le code: {}", codeSalle);
        return salleRepository.findById(codeSalle);
    }

    // Récupérer les salles disponibles
    @Transactional(readOnly = true)
    public List<Salle> obtenirSallesDisponibles() {
        log.info("Récupération des salles disponibles");
        return salleRepository.findByDisponibilite(true);
    }

    // Récupérer les salles par type
    @Transactional(readOnly = true)
    public List<Salle> obtenirSallesParType(String typeSalle) {
        log.info("Récupération des salles de type: {}", typeSalle);
        return salleRepository.findByTypeSalle(typeSalle);
    }

    // Récupérer les salles par bâtiment
    @Transactional(readOnly = true)
    public List<Salle> obtenirSallesParBatiment(String batiment) {
        log.info("Récupération des salles du bâtiment: {}", batiment);
        return salleRepository.findByBatiment(batiment);
    }

    // Récupérer les salles avec capacité minimale
    @Transactional(readOnly = true)
    public List<Salle> obtenirSallesAvecCapaciteMin(Integer capaciteMin) {
        log.info("Récupération des salles avec capacité >= {}", capaciteMin);
        return salleRepository.findByCapaciteGreaterThanEqual(capaciteMin);
    }

    // Rechercher des salles par nom
    @Transactional(readOnly = true)
    public List<Salle> rechercherSallesParNom(String nomSalle) {
        log.info("Recherche de salles par nom: {}", nomSalle);
        return salleRepository.findByNomSalleContainingIgnoreCase(nomSalle);
    }

    // Récupérer les salles disponibles pour une période
    @Transactional(readOnly = true)
    public List<Salle> obtenirSallesDisponiblesPourPeriode(LocalDate jour, LocalTime heureDebut, LocalTime heureFin) {
        log.info("Récupération des salles disponibles le {} de {} à {}", jour, heureDebut, heureFin);
        return salleRepository.findSallesDisponiblesPourPeriode(jour, heureDebut, heureFin);
    }

    // Récupérer les salles disponibles avec capacité suffisante
    @Transactional(readOnly = true)
    public List<Salle> obtenirSallesDisponiblesAvecCapacite(LocalDate jour, LocalTime heureDebut,
                                                            LocalTime heureFin, Integer capaciteMin) {
        log.info("Récupération des salles disponibles le {} de {} à {} avec capacité >= {}",
                jour, heureDebut, heureFin, capaciteMin);
        return salleRepository.findSallesDisponiblesAvecCapacite(jour, heureDebut, heureFin, capaciteMin);
    }

    // Mettre à jour une salle
    public Salle mettreAJourSalle(String codeSalle, Salle salleModifiee) {
        log.info("Mise à jour de la salle avec le code: {}", codeSalle);

        return salleRepository.findById(codeSalle)
                .map(salle -> {
                    salle.setNomSalle(salleModifiee.getNomSalle());
                    salle.setCapacite(salleModifiee.getCapacite());
                    salle.setDisponibilite(salleModifiee.getDisponibilite());
                    salle.setTypeSalle(salleModifiee.getTypeSalle());
                    salle.setBatiment(salleModifiee.getBatiment());
                    salle.setEtage(salleModifiee.getEtage());
                    salle.setEquipements(salleModifiee.getEquipements());

                    return salleRepository.save(salle);
                })
                .orElseThrow(() -> new IllegalArgumentException("Salle non trouvée avec le code: " + codeSalle));
    }

    // Modifier la disponibilité d'une salle
    public Salle modifierDisponibiliteSalle(String codeSalle, Boolean disponibilite) {
        log.info("Modification de la disponibilité de la salle {} à {}", codeSalle, disponibilite);

        return salleRepository.findById(codeSalle)
                .map(salle -> {
                    salle.setDisponibilite(disponibilite);
                    return salleRepository.save(salle);
                })
                .orElseThrow(() -> new IllegalArgumentException("Salle non trouvée avec le code: " + codeSalle));
    }

    // Supprimer une salle
    public void supprimerSalle(String codeSalle) {
        log.info("Suppression de la salle avec le code: {}", codeSalle);

        if (!salleRepository.existsById(codeSalle)) {
            throw new IllegalArgumentException("Salle non trouvée avec le code: " + codeSalle);
        }

        salleRepository.deleteById(codeSalle);
    }

    // Vérifier si une salle existe
    @Transactional(readOnly = true)
    public boolean salleExiste(String codeSalle) {
        return salleRepository.existsById(codeSalle);
    }
}