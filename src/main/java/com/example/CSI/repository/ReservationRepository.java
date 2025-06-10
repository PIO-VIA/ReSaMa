package com.example.CSI.repository;


import com.example.CSI.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Recherche par enseignant
    List<Reservation> findByEnseignantIdEnseignant(Long idEnseignant);

    // Recherche par salle
    List<Reservation> findBySalleCodeSalle(String codeSalle);

    // Recherche par matériel
    List<Reservation> findByMaterielCodeMateriel(String codeMateriel);

    // Recherche par formation
    List<Reservation> findByFormationIdFormation(Long idFormation);

    // Recherche par date
    List<Reservation> findByJour(LocalDate jour);

    // Recherche par période
    List<Reservation> findByJourBetween(LocalDate dateDebut, LocalDate dateFin);

    // Recherche par statut
    List<Reservation> findByStatut(Reservation.StatutReservation statut);

    // Planning complet d'une salle pour une période
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.salle.codeSalle = :codeSalle " +
            "AND r.jour BETWEEN :dateDebut AND :dateFin " +
            "ORDER BY r.jour, r.heureDebut")
    List<Reservation> findPlanningSalle(@Param("codeSalle") String codeSalle,
                                        @Param("dateDebut") LocalDate dateDebut,
                                        @Param("dateFin") LocalDate dateFin);

    // Planning d'un enseignant pour une période
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.enseignant.idEnseignant = :idEnseignant " +
            "AND r.jour BETWEEN :dateDebut AND :dateFin " +
            "ORDER BY r.jour, r.heureDebut")
    List<Reservation> findPlanningEnseignant(@Param("idEnseignant") Long idEnseignant,
                                             @Param("dateDebut") LocalDate dateDebut,
                                             @Param("dateFin") LocalDate dateFin);

    // Vérifier conflit de réservation pour une salle
    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.salle.codeSalle = :codeSalle " +
            "AND r.jour = :jour " +
            "AND r.numero != :reservationId " +
            "AND ((r.heureDebut <= :heureDebut AND r.heureFin > :heureDebut) " +
            "OR (r.heureDebut < :heureFin AND r.heureFin >= :heureFin) " +
            "OR (r.heureDebut >= :heureDebut AND r.heureFin <= :heureFin))")
    boolean existsConflitSalle(@Param("codeSalle") String codeSalle,
                               @Param("jour") LocalDate jour,
                               @Param("heureDebut") LocalTime heureDebut,
                               @Param("heureFin") LocalTime heureFin,
                               @Param("reservationId") Long reservationId);

    // Vérifier conflit de réservation pour un matériel
    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.materiel.codeMateriel = :codeMateriel " +
            "AND r.jour = :jour " +
            "AND r.numero != :reservationId " +
            "AND ((r.heureDebut <= :heureDebut AND r.heureFin > :heureDebut) " +
            "OR (r.heureDebut < :heureFin AND r.heureFin >= :heureFin) " +
            "OR (r.heureDebut >= :heureDebut AND r.heureFin <= :heureFin))")
    boolean existsConflitMateriel(@Param("codeMateriel") String codeMateriel,
                                  @Param("jour") LocalDate jour,
                                  @Param("heureDebut") LocalTime heureDebut,
                                  @Param("heureFin") LocalTime heureFin,
                                  @Param("reservationId") Long reservationId);

    // Récapitulatif horaire par formation
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.formation.idFormation = :idFormation " +
            "AND r.jour BETWEEN :dateDebut AND :dateFin " +
            "ORDER BY r.jour, r.heureDebut")
    List<Reservation> findRecapitulatifFormation(@Param("idFormation") Long idFormation,
                                                 @Param("dateDebut") LocalDate dateDebut,
                                                 @Param("dateFin") LocalDate dateFin);
}
