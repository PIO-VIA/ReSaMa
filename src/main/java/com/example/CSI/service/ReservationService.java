package com.example.CSI.service;


import com.example.CSI.model.*;
import com.example.CSI.repository.*;
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
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EnseignantRepository enseignantRepository;
    private final SalleRepository salleRepository;
    private final MaterielRepository materielRepository;
    private final FormationRepository formationRepository;

    // Créer une nouvelle réservation
    public Reservation creerReservation(Reservation reservation) {
        log.info("Création d'une nouvelle réservation pour le {}", reservation.getJour());

        // Validations
        validerReservation(reservation);

        // Vérifier les conflits
        verifierConflits(reservation);

        return reservationRepository.save(reservation);
    }

    // Récupérer toutes les réservations
    @Transactional(readOnly = true)
    public List<Reservation> obtenirToutesLesReservations() {
        log.info("Récupération de toutes les réservations");
        return reservationRepository.findAll();
    }

    // Récupérer une réservation par ID
    @Transactional(readOnly = true)
    public Optional<Reservation> obtenirReservationParId(Long id) {
        log.info("Récupération de la réservation avec l'ID: {}", id);
        return reservationRepository.findById(id);
    }

    // Récupérer les réservations d'un enseignant
    @Transactional(readOnly = true)
    public List<Reservation> obtenirReservationsParEnseignant(Long idEnseignant) {
        log.info("Récupération des réservations pour l'enseignant: {}", idEnseignant);
        return reservationRepository.findByEnseignantIdEnseignant(idEnseignant);
    }

    // Récupérer le planning d'une salle
    @Transactional(readOnly = true)
    public List<Reservation> obtenirPlanningSalle(String codeSalle, LocalDate dateDebut, LocalDate dateFin) {
        log.info("Récupération du planning de la salle {} du {} au {}", codeSalle, dateDebut, dateFin);
        return reservationRepository.findPlanningSalle(codeSalle, dateDebut, dateFin);
    }

    // Récupérer le planning d'un enseignant
    @Transactional(readOnly = true)
    public List<Reservation> obtenirPlanningEnseignant(Long idEnseignant, LocalDate dateDebut, LocalDate dateFin) {
        log.info("Récupération du planning de l'enseignant {} du {} au {}", idEnseignant, dateDebut, dateFin);
        return reservationRepository.findPlanningEnseignant(idEnseignant, dateDebut, dateFin);
    }

    // Récupérer les réservations par date
    @Transactional(readOnly = true)
    public List<Reservation> obtenirReservationsParDate(LocalDate date) {
        log.info("Récupération des réservations pour la date: {}", date);
        return reservationRepository.findByJour(date);
    }

    // Récupérer les réservations par période
    @Transactional(readOnly = true)
    public List<Reservation> obtenirReservationsParPeriode(LocalDate dateDebut, LocalDate dateFin) {
        log.info("Récupération des réservations du {} au {}", dateDebut, dateFin);
        return reservationRepository.findByJourBetween(dateDebut, dateFin);
    }

    // Récupérer le récapitulatif d'une formation
    @Transactional(readOnly = true)
    public List<Reservation> obtenirRecapitulatifFormation(Long idFormation, LocalDate dateDebut, LocalDate dateFin) {
        log.info("Récupération du récapitulatif de la formation {} du {} au {}", idFormation, dateDebut, dateFin);
        return reservationRepository.findRecapitulatifFormation(idFormation, dateDebut, dateFin);
    }

    // Mettre à jour une réservation
    public Reservation mettreAJourReservation(Long id, Reservation reservationModifiee) {
        log.info("Mise à jour de la réservation avec l'ID: {}", id);

        return reservationRepository.findById(id)
                .map(reservation -> {
                    // Conserver l'ID pour les vérifications de conflit
                    reservationModifiee.setNumero(id);

                    // Validations
                    validerReservation(reservationModifiee);

                    // Vérifier les conflits (en excluant cette réservation)
                    verifierConflits(reservationModifiee);

                    // Mettre à jour les champs
                    reservation.setJour(reservationModifiee.getJour());
                    reservation.setHeureDebut(reservationModifiee.getHeureDebut());
                    reservation.setHeureFin(reservationModifiee.getHeureFin());
                    reservation.setMotif(reservationModifiee.getMotif());
                    reservation.setNombreParticipants(reservationModifiee.getNombreParticipants());
                    reservation.setStatut(reservationModifiee.getStatut());
                    reservation.setEnseignant(reservationModifiee.getEnseignant());
                    reservation.setSalle(reservationModifiee.getSalle());
                    reservation.setMateriel(reservationModifiee.getMateriel());
                    reservation.setFormation(reservationModifiee.getFormation());

                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée avec l'ID: " + id));
    }

    // Annuler une réservation
    public Reservation annulerReservation(Long id) {
        log.info("Annulation de la réservation avec l'ID: {}", id);

        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setStatut(Reservation.StatutReservation.ANNULEE);
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée avec l'ID: " + id));
    }

    // Supprimer une réservation
    public void supprimerReservation(Long id) {
        log.info("Suppression de la réservation avec l'ID: {}", id);

        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Réservation non trouvée avec l'ID: " + id);
        }

        reservationRepository.deleteById(id);
    }

    // Méthodes privées de validation
    private void validerReservation(Reservation reservation) {
        // Vérifier que l'heure de fin est après l'heure de début
        if (reservation.getHeureFin().isBefore(reservation.getHeureDebut()) ||
                reservation.getHeureFin().equals(reservation.getHeureDebut())) {
            throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début");
        }

        // Vérifier que la date n'est pas dans le passé
        if (reservation.getJour().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Impossible de faire une réservation dans le passé");
        }

        // Vérifier que l'enseignant existe
        if (!enseignantRepository.existsById(reservation.getEnseignant().getIdEnseignant())) {
            throw new IllegalArgumentException("Enseignant non trouvé");
        }

        // Vérifier que la salle existe et est disponible
        Salle salle = salleRepository.findById(reservation.getSalle().getCodeSalle())
                .orElseThrow(() -> new IllegalArgumentException("Salle non trouvée"));

        if (!salle.getDisponibilite()) {
            throw new IllegalArgumentException("La salle n'est pas disponible");
        }

        // Vérifier la capacité de la salle
        if (reservation.getNombreParticipants() != null &&
                reservation.getNombreParticipants() > salle.getCapacite()) {
            throw new IllegalArgumentException("Le nombre de participants dépasse la capacité de la salle");
        }

        // Vérifier le matériel s'il est spécifié
        if (reservation.getMateriel() != null) {
            Materiel materiel = materielRepository.findById(reservation.getMateriel().getCodeMateriel())
                    .orElseThrow(() -> new IllegalArgumentException("Matériel non trouvé"));

            if (!materiel.getDisponibilite()) {
                throw new IllegalArgumentException("Le matériel n'est pas disponible");
            }
        }

        // Vérifier la formation si spécifiée
        if (reservation.getFormation() != null) {
            if (!formationRepository.existsById(reservation.getFormation().getIdFormation())) {
                throw new IllegalArgumentException("Formation non trouvée");
            }
        }
    }

    private void verifierConflits(Reservation reservation) {
        Long reservationId = reservation.getNumero() != null ? reservation.getNumero() : -1L;

        // Vérifier conflit salle
        if (reservationRepository.existsConflitSalle(
                reservation.getSalle().getCodeSalle(),
                reservation.getJour(),
                reservation.getHeureDebut(),
                reservation.getHeureFin(),
                reservationId)) {
            throw new IllegalArgumentException("Conflit de réservation pour la salle");
        }

        // Vérifier conflit matériel
        if (reservation.getMateriel() != null &&
                reservationRepository.existsConflitMateriel(
                        reservation.getMateriel().getCodeMateriel(),
                        reservation.getJour(),
                        reservation.getHeureDebut(),
                        reservation.getHeureFin(),
                        reservationId)) {
            throw new IllegalArgumentException("Conflit de réservation pour le matériel");
        }
    }
}
