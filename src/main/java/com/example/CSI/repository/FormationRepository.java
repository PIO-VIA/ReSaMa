package com.example.CSI.repository;


import com.example.CSI.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    // Recherche par code formation
    Optional<Formation> findByCodeFormation(String codeFormation);

    // Recherche par nom de formation contenant
    List<Formation> findByNomFormationContainingIgnoreCase(String nomFormation);

    // Recherche par niveau
    List<Formation> findByNiveau(String niveau);

    // Recherche par responsable
    List<Formation> findByResponsableIdEnseignant(Long idResponsable);

    // Vérifier si un code formation existe déjà
    boolean existsByCodeFormation(String codeFormation);

    // Récupérer les formations avec leurs réservations
    @Query("SELECT DISTINCT f FROM Formation f LEFT JOIN FETCH f.reservations r")
    List<Formation> findAllWithReservations();

    // Récupérer les formations d'un responsable avec détails
    @Query("SELECT f FROM Formation f LEFT JOIN FETCH f.responsable " +
            "WHERE f.responsable.idEnseignant = :idResponsable")
    List<Formation> findByResponsableWithDetails(@Param("idResponsable") Long idResponsable);
}