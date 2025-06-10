package com.example.CSI.repository;


import com.example.CSI.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SalleRepository extends JpaRepository<Salle, String> {

    // Recherche par disponibilité
    List<Salle> findByDisponibilite(Boolean disponibilite);

    // Recherche par type de salle
    List<Salle> findByTypeSalle(String typeSalle);

    // Recherche par bâtiment
    List<Salle> findByBatiment(String batiment);

    // Recherche par capacité minimale
    List<Salle> findByCapaciteGreaterThanEqual(Integer capaciteMin);

    // Recherche par nom contenant
    List<Salle> findByNomSalleContainingIgnoreCase(String nomSalle);

    // Recherche des salles disponibles pour une période donnée
    @Query("SELECT s FROM Salle s WHERE s.disponibilite = true " +
            "AND s.codeSalle NOT IN (" +
            "SELECT r.salle.codeSalle FROM Reservation r " +
            "WHERE r.jour = :jour " +
            "AND ((r.heureDebut <= :heureDebut AND r.heureFin > :heureDebut) " +
            "OR (r.heureDebut < :heureFin AND r.heureFin >= :heureFin) " +
            "OR (r.heureDebut >= :heureDebut AND r.heureFin <= :heureFin)))")
    List<Salle> findSallesDisponiblesPourPeriode(@Param("jour") LocalDate jour,
                                                 @Param("heureDebut") LocalTime heureDebut,
                                                 @Param("heureFin") LocalTime heureFin);

    // Recherche des salles avec une capacité suffisante et disponibles
    @Query("SELECT s FROM Salle s WHERE s.disponibilite = true " +
            "AND s.capacite >= :capaciteMin " +
            "AND s.codeSalle NOT IN (" +
            "SELECT r.salle.codeSalle FROM Reservation r " +
            "WHERE r.jour = :jour " +
            "AND ((r.heureDebut <= :heureDebut AND r.heureFin > :heureDebut) " +
            "OR (r.heureDebut < :heureFin AND r.heureFin >= :heureFin) " +
            "OR (r.heureDebut >= :heureDebut AND r.heureFin <= :heureFin)))")
    List<Salle> findSallesDisponiblesAvecCapacite(@Param("jour") LocalDate jour,
                                                  @Param("heureDebut") LocalTime heureDebut,
                                                  @Param("heureFin") LocalTime heureFin,
                                                  @Param("capaciteMin") Integer capaciteMin);
}