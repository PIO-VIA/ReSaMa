package com.example.CSI.repository;


import com.example.CSI.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    // Recherche par email
    Optional<Enseignant> findByEmail(String email);

    // Recherche par nom et prénom
    Optional<Enseignant> findByNomEnseignantAndPrenomEnseignant(String nom, String prenom);

    // Recherche par spécialité
    List<Enseignant> findBySpecialite(String specialite);

    // Recherche par nom contenant (pour la recherche)
    List<Enseignant> findByNomEnseignantContainingIgnoreCase(String nom);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);

    // Récupérer tous les enseignants responsables de formations
    @Query("SELECT DISTINCT e FROM Enseignant e JOIN e.formationsResponsables f")
    List<Enseignant> findEnseignantsResponsables();

    // Récupérer les enseignants avec leurs réservations pour une période
    @Query("SELECT DISTINCT e FROM Enseignant e LEFT JOIN FETCH e.reservations r " +
            "WHERE r.jour BETWEEN :dateDebut AND :dateFin")
    List<Enseignant> findEnseignantsWithReservationsBetween(@Param("dateDebut") java.time.LocalDate dateDebut,
                                                            @Param("dateFin") java.time.LocalDate dateFin);
}