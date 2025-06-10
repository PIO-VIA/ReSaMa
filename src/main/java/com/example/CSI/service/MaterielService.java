package com.example.CSI.service;


import com.example.CSI.model.*;
import com.example.CSI.repository.MaterielRepository;
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
public class MaterielService {

    private final MaterielRepository materielRepository;

    // Créer un nouveau matériel
    public Materiel creerMateriel(Materiel materiel) {
        log.info("Création d'un nouveau matériel: {}", materiel.getCodeMateriel());

        if (materielRepository.existsById(materiel.getCodeMateriel())) {
            throw new IllegalArgumentException("Un matériel avec ce code existe déjà");
        }

        return materielRepository.save(materiel);
    }

    // Récupérer tous les matériels
    @Transactional(readOnly = true)
    public List<Materiel> obtenirTousLesMateriels() {
        log.info("Récupération de tous les matériels");
        return materielRepository.findAll();
    }

    // Récupérer un matériel par code
    @Transactional(readOnly = true)
    public Optional<Materiel> obtenirMaterielParCode(String codeMateriel) {
        log.info("Récupération du matériel avec le code: {}", codeMateriel);
        return materielRepository.findById(codeMateriel);
    }

    // Récupérer les matériels disponibles
    @Transactional(readOnly = true)
    public List<Materiel> obtenirMaterielsDisponibles() {
        log.info("Récupération des matériels disponibles");
        return materielRepository.findByDisponibilite(true);
    }

    // Récupérer les matériels par marque
    @Transactional(readOnly = true)
    public List<Materiel> obtenirMaterielsParMarque(String marque) {
        log.info("Récupération des matériels par marque: {}", marque);
        return materielRepository.findByMarque(marque);
    }

    // Récupérer les matériels par état
    @Transactional(readOnly = true)
    public List<Materiel> obtenirMaterielsParEtat(String etat) {
        log.info("Récupération des matériels par état: {}", etat);
        return materielRepository.findByEtat(etat);
    }

    // Récupérer les matériels par localisation
    @Transactional(readOnly = true)
    public List<Materiel> obtenirMaterielsParLocalisation(String localisation) {
        log.info("Récupération des matériels par localisation: {}", localisation);
        return materielRepository.findByLocalisation(localisation);
    }

    // Récupérer les ordinateurs uniquement
    @Transactional(readOnly = true)
    public List<Materiel> obtenirOrdinateurs() {
        log.info("Récupération des ordinateurs");
        return materielRepository.findOrdinateurs();
    }

    // Récupérer les vidéoprojecteurs uniquement
    @Transactional(readOnly = true)
    public List<Materiel> obtenirVideoProjecteurs() {
        log.info("Récupération des vidéoprojecteurs");
        return materielRepository.findVideoProjecteurs();
    }

    // Récupérer les matériels par type
    @Transactional(readOnly = true)
    public List<Materiel> obtenirMaterielsParType(Class<? extends Materiel> typeMateriel) {
        log.info("Récupération des matériels de type: {}", typeMateriel.getSimpleName());
        return materielRepository.findByTypeMateriel(typeMateriel);
    }

    // Récupérer les matériels disponibles pour une période
    @Transactional(readOnly = true)
    public List<Materiel> obtenirMaterielsDisponiblesPourPeriode(LocalDate jour, LocalTime heureDebut, LocalTime heureFin) {
        log.info("Récupération des matériels disponibles le {} de {} à {}", jour, heureDebut, heureFin);
        return materielRepository.findMaterielsDisponiblesPourPeriode(jour, heureDebut, heureFin);
    }

    // Mettre à jour un matériel
    public Materiel mettreAJourMateriel(String codeMateriel, Materiel materielModifie) {
        log.info("Mise à jour du matériel avec le code: {}", codeMateriel);

        return materielRepository.findById(codeMateriel)
                .map(materiel -> {
                    // Mettre à jour les propriétés communes
                    materiel.setDisponibilite(materielModifie.getDisponibilite());
                    materiel.setMarque(materielModifie.getMarque());
                    materiel.setModele(materielModifie.getModele());
                    materiel.setEtat(materielModifie.getEtat());
                    materiel.setDateAcquisition(materielModifie.getDateAcquisition());
                    materiel.setLocalisation(materielModifie.getLocalisation());

                    // Mettre à jour les propriétés spécifiques selon le type
                    if (materiel instanceof Ordinateur && materielModifie instanceof Ordinateur) {
                        Ordinateur ordinateur = (Ordinateur) materiel;
                        Ordinateur ordinateurModifie = (Ordinateur) materielModifie;

                        ordinateur.setProcesseur(ordinateurModifie.getProcesseur());
                        ordinateur.setRam(ordinateurModifie.getRam());
                        ordinateur.setStockage(ordinateurModifie.getStockage());
                        ordinateur.setTailleEcran(ordinateurModifie.getTailleEcran());
                        ordinateur.setSystemeExploitation(ordinateurModifie.getSystemeExploitation());
                        ordinateur.setTypeOrdinateur(ordinateurModifie.getTypeOrdinateur());

                    } else if (materiel instanceof VideoProjecteur && materielModifie instanceof VideoProjecteur) {
                        VideoProjecteur videoProjecteur = (VideoProjecteur) materiel;
                        VideoProjecteur videoProjecteurModifie = (VideoProjecteur) materielModifie;

                        videoProjecteur.setDescription(videoProjecteurModifie.getDescription());
                        videoProjecteur.setResolution(videoProjecteurModifie.getResolution());
                        videoProjecteur.setLuminosite(videoProjecteurModifie.getLuminosite());
                        videoProjecteur.setConnectivite(videoProjecteurModifie.getConnectivite());
                        videoProjecteur.setPoids(videoProjecteurModifie.getPoids());
                        videoProjecteur.setTypeProjection(videoProjecteurModifie.getTypeProjection());
                    }

                    return materielRepository.save(materiel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Matériel non trouvé avec le code: " + codeMateriel));
    }

    // Modifier la disponibilité d'un matériel
    public Materiel modifierDisponibiliteMateriel(String codeMateriel, Boolean disponibilite) {
        log.info("Modification de la disponibilité du matériel {} à {}", codeMateriel, disponibilite);

        return materielRepository.findById(codeMateriel)
                .map(materiel -> {
                    materiel.setDisponibilite(disponibilite);
                    return materielRepository.save(materiel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Matériel non trouvé avec le code: " + codeMateriel));
    }

    // Modifier l'état d'un matériel
    public Materiel modifierEtatMateriel(String codeMateriel, String nouvelEtat) {
        log.info("Modification de l'état du matériel {} à {}", codeMateriel, nouvelEtat);

        return materielRepository.findById(codeMateriel)
                .map(materiel -> {
                    materiel.setEtat(nouvelEtat);
                    // Si l'état est "Défaillant", rendre le matériel indisponible
                    if ("Défaillant".equalsIgnoreCase(nouvelEtat)) {
                        materiel.setDisponibilite(false);
                    }
                    return materielRepository.save(materiel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Matériel non trouvé avec le code: " + codeMateriel));
    }

    // Déplacer un matériel (changer sa localisation)
    public Materiel deplacerMateriel(String codeMateriel, String nouvelleLocalisation) {
        log.info("Déplacement du matériel {} vers {}", codeMateriel, nouvelleLocalisation);

        return materielRepository.findById(codeMateriel)
                .map(materiel -> {
                    materiel.setLocalisation(nouvelleLocalisation);
                    return materielRepository.save(materiel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Matériel non trouvé avec le code: " + codeMateriel));
    }

    // Supprimer un matériel
    public void supprimerMateriel(String codeMateriel) {
        log.info("Suppression du matériel avec le code: {}", codeMateriel);

        if (!materielRepository.existsById(codeMateriel)) {
            throw new IllegalArgumentException("Matériel non trouvé avec le code: " + codeMateriel);
        }

        materielRepository.deleteById(codeMateriel);
    }

    // Vérifier si un matériel existe
    @Transactional(readOnly = true)
    public boolean materielExiste(String codeMateriel) {
        return materielRepository.existsById(codeMateriel);
    }
}