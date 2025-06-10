package com.example.CSI.controller;

import com.example.CSI.model.Reservation;
import com.example.CSI.service.ReservationService;
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
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    // Créer une nouvelle réservation
    @PostMapping
    public ResponseEntity<Reservation> creerReservation(@Valid @RequestBody Reservation reservation) {
        log.info("Demande de création de réservation pour le {}", reservation.getJour());

        try {
            Reservation nouvelleReservation = reservationService.creerReservation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleReservation);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la création de la réservation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer toutes les réservations
    @GetMapping
    public ResponseEntity<List<Reservation>> obtenirToutesLesReservations() {
        log.info("Demande de récupération de toutes les réservations");
        List<Reservation> reservations = reservationService.obtenirToutesLesReservations();
        return ResponseEntity.ok(reservations);
    }

    // Récupérer une réservation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> obtenirReservationParId(@PathVariable Long id) {
        log.info("Demande de récupération de la réservation avec l'ID: {}", id);

        return reservationService.obtenirReservationParId(id)
                .map(reservation -> ResponseEntity.ok(reservation))
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les réservations d'un enseignant
    @GetMapping("/enseignant/{idEnseignant}")
    public ResponseEntity<List<Reservation>> obtenirReservationsParEnseignant(@PathVariable Long idEnseignant) {
        log.info("Demande de récupération des réservations pour l'enseignant: {}", idEnseignant);
        List<Reservation> reservations = reservationService.obtenirReservationsParEnseignant(idEnseignant);
        return ResponseEntity.ok(reservations);
    }

    // Récupérer le planning d'une salle
    @GetMapping("/salle/{codeSalle}/planning")
    public ResponseEntity<List<Reservation>> obtenirPlanningSalle(
            @PathVariable String codeSalle,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        log.info("Demande de récupération du planning de la salle {} du {} au {}", codeSalle, dateDebut, dateFin);
        List<Reservation> reservations = reservationService.obtenirPlanningSalle(codeSalle, dateDebut, dateFin);
        return ResponseEntity.ok(reservations);
    }

    // Récupérer le planning d'un enseignant
    @GetMapping("/enseignant/{idEnseignant}/planning")
    public ResponseEntity<List<Reservation>> obtenirPlanningEnseignant(
            @PathVariable Long idEnseignant,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        log.info("Demande de récupération du planning de l'enseignant {} du {} au {}", idEnseignant, dateDebut, dateFin);
        List<Reservation> reservations = reservationService.obtenirPlanningEnseignant(idEnseignant, dateDebut, dateFin);
        return ResponseEntity.ok(reservations);
    }

    // Récupérer les réservations par date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Reservation>> obtenirReservationsParDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Demande de récupération des réservations pour la date: {}", date);
        List<Reservation> reservations = reservationService.obtenirReservationsParDate(date);
        return ResponseEntity.ok(reservations);
    }

    // Récupérer les réservations par période
    @GetMapping("/periode")
    public ResponseEntity<List<Reservation>> obtenirReservationsParPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        log.info("Demande de récupération des réservations du {} au {}", dateDebut, dateFin);
        List<Reservation> reservations = reservationService.obtenirReservationsParPeriode(dateDebut, dateFin);
        return ResponseEntity.ok(reservations);
    }

    // Récupérer le récapitulatif d'une formation
    @GetMapping("/formation/{idFormation}/recapitulatif")
    public ResponseEntity<List<Reservation>> obtenirRecapitulatifFormation(
            @PathVariable Long idFormation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        log.info("Demande de récupération du récapitulatif de la formation {} du {} au {}", idFormation, dateDebut, dateFin);
        List<Reservation> reservations = reservationService.obtenirRecapitulatifFormation(idFormation, dateDebut, dateFin);
        return ResponseEntity.ok(reservations);
    }

    // Mettre à jour une réservation
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> mettreAJourReservation(@PathVariable Long id,
                                                              @Valid @RequestBody Reservation reservation) {
        log.info("Demande de mise à jour de la réservation avec l'ID: {}", id);

        try {
            Reservation reservationMiseAJour = reservationService.mettreAJourReservation(id, reservation);
            return ResponseEntity.ok(reservationMiseAJour);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la mise à jour de la réservation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Annuler une réservation
    @PatchMapping("/{id}/annuler")
    public ResponseEntity<Reservation> annulerReservation(@PathVariable Long id) {
        log.info("Demande d'annulation de la réservation avec l'ID: {}", id);

        try {
            Reservation reservationAnnulee = reservationService.annulerReservation(id);
            return ResponseEntity.ok(reservationAnnulee);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de l'annulation de la réservation: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerReservation(@PathVariable Long id) {
        log.info("Demande de suppression de la réservation avec l'ID: {}", id);

        try {
            reservationService.supprimerReservation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la suppression de la réservation: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}