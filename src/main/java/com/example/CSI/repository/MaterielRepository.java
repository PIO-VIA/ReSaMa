package com.example.CSI.repository;


import com.example.CSI.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel, String> {

    // Recherche par disponibilité
    List<Materiel> findByDisponibilite(Boolean disponibilite);

    // Recherche par marque
    List<Materiel> findByMarque(String marque);

    // Recherche par état
    List<Materiel> findByEtat(String etat);

    // Recherche par type de matériel (discriminator)
    @Query("SELECT m FROM Materiel m WHERE TYPE(m) = :typeMateriel")
    List<Materiel> findByTypeMateriel(@Param("typeMateriel") Class<? extends Materiel> typeMateriel);

    // Recherche des ordinateurs uniquement
    @Query("SELECT m FROM Materiel m WHERE TYPE(m) = com.example.CSI.model.Ordinateur")
    List<Materiel> findOrdinateurs();

    // Recherche des vidéoprojecteurs uniquement
    @Query("SELECT m FROM Materiel m WHERE TYPE(m) = com.example.CSI.model.VideoProjecteur")
    List<Materiel> findVideoProjecteurs();

    // Recherche des matériels disponibles pour une période donnée
    @Query("SELECT m FROM Materiel m WHERE m.disponibilite = true " +
            "AND m.codeMateriel NOT IN (" +
            "SELECT r.materiel.codeMateriel FROM Reservation r " +
            "WHERE r.materiel IS NOT NULL " +
            "AND r.jour = :jour " +
            "AND ((r.heureDebut <= :heureDebut AND r.heureFin > :heureDebut) " +
            "OR (r.heureDebut < :heureFin AND r.heureFin >= :heureFin) " +
            "OR (r.heureDebut >= :heureDebut AND r.heureFin <= :heureFin)))")
    List<Materiel> findMaterielsDisponiblesPourPeriode(@Param("jour") LocalDate jour,
                                                       @Param("heureDebut") LocalTime heureDebut,
                                                       @Param("heureFin") LocalTime heureFin);

    // Recherche des matériels par localisation
    List<Materiel> findByLocalisation(String localisation);
}
