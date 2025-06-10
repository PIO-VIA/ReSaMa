package com.example.CSI.config;


import com.example.CSI.model.*;
import com.example.CSI.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final EnseignantRepository enseignantRepository;
    private final FormationRepository formationRepository;
    private final SalleRepository salleRepository;
    private final MaterielRepository materielRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        if (enseignantRepository.count() == 0) {
            log.info("🔄 Chargement des données initiales...");
            chargerDonneesInitiales();
            log.info("✅ Données initiales chargées avec succès!");
        } else {
            log.info("📊 Données déjà présentes, pas de chargement initial nécessaire");
        }
    }

    private void chargerDonneesInitiales() {
        // Créer des enseignants
        Enseignant enseignant1 = new Enseignant("Dupont", "Jean", "jean.dupont@etablissement.fr",
                "0123456789", "Informatique");
        Enseignant enseignant2 = new Enseignant("Martin", "Marie", "marie.martin@etablissement.fr",
                "0123456790", "Mathématiques");
        Enseignant enseignant3 = new Enseignant("Bernard", "Pierre", "pierre.bernard@etablissement.fr",
                "0123456791", "Physique");

        enseignant1 = enseignantRepository.save(enseignant1);
        enseignant2 = enseignantRepository.save(enseignant2);
        enseignant3 = enseignantRepository.save(enseignant3);
        log.info("👥 {} enseignants créés", 3);

        // Créer des formations
        Formation formation1 = new Formation("INFO-L3", "Licence 3 Informatique",
                "Formation en développement logiciel", "L3", 600, enseignant1);
        Formation formation2 = new Formation("MATH-M1", "Master 1 Mathématiques",
                "Formation avancée en mathématiques", "M1", 480, enseignant2);
        Formation formation3 = new Formation("PHYS-L2", "Licence 2 Physique",
                "Formation en physique générale", "L2", 520, enseignant3);

        formation1 = formationRepository.save(formation1);
        formation2 = formationRepository.save(formation2);
        formation3 = formationRepository.save(formation3);
        log.info("🎓 {} formations créées", 3);

        // Créer des salles
        Salle salle1 = new Salle("A101", "Salle TP Informatique", 24, "TP", "Bâtiment A", "1er étage",
                "24 postes informatiques, tableau interactif");
        Salle salle2 = new Salle("B205", "Amphithéâtre", 120, "Amphi", "Bâtiment B", "2ème étage",
                "Système audio, vidéoprojecteur fixe");
        Salle salle3 = new Salle("C103", "Salle de cours", 30, "Cours", "Bâtiment C", "1er étage",
                "Tableau blanc, prises électriques");
        Salle salle4 = new Salle("A204", "Laboratoire Physique", 16, "Laboratoire", "Bâtiment A", "2ème étage",
                "Équipements de laboratoire, hottes");

        salleRepository.save(salle1);
        salleRepository.save(salle2);
        salleRepository.save(salle3);
        salleRepository.save(salle4);
        log.info("🏢 {} salles créées", 4);

        // Créer des ordinateurs
        Ordinateur ordinateur1 = new Ordinateur("PC001", "Dell", "Latitude 5520", "Bon",
                "2023-01-15", "Magasin informatique", "Intel i5-11400H", "16GB", "512GB SSD",
                "15.6\"", "Windows 11", "Portable");
        Ordinateur ordinateur2 = new Ordinateur("PC002", "HP", "EliteBook 840", "Bon",
                "2023-02-20", "Magasin informatique", "Intel i7-1165G7", "16GB", "1TB SSD",
                "14\"", "Windows 11", "Portable");
        Ordinateur ordinateur3 = new Ordinateur("PC003", "Lenovo", "ThinkPad T14", "Moyen",
                "2022-09-10", "Salle A101", "AMD Ryzen 5", "8GB", "256GB SSD",
                "14\"", "Windows 10", "Portable");

        materielRepository.save(ordinateur1);
        materielRepository.save(ordinateur2);
        materielRepository.save(ordinateur3);

        // Créer des vidéoprojecteurs
        VideoProjecteur projecteur1 = new VideoProjecteur("VP001", "Epson", "EB-X41", "Bon",
                "2023-03-10", "Magasin audiovisuel", "Vidéoprojecteur portable polyvalent",
                "1024x768", "3600 lumens", "HDMI, VGA, USB", 2.5, "3LCD");
        VideoProjecteur projecteur2 = new VideoProjecteur("VP002", "BenQ", "MX550", "Bon",
                "2023-01-25", "Magasin audiovisuel", "Vidéoprojecteur business",
                "1024x768", "3600 lumens", "HDMI, VGA", 2.2, "DLP");
        VideoProjecteur projecteur3 = new VideoProjecteur("VP003", "Canon", "LV-X320", "Défaillant",
                "2021-11-15", "Atelier réparation", "Vidéoprojecteur multimédia",
                "1024x768", "3200 lumens", "HDMI, VGA, USB", 2.8, "3LCD");

        materielRepository.save(projecteur1);
        materielRepository.save(projecteur2);
        materielRepository.save(projecteur3);
        log.info("💻 {} matériels créés (ordinateurs et vidéoprojecteurs)", 6);

        // Créer quelques réservations
        LocalDate aujourd_hui = LocalDate.now();
        LocalDate demain = aujourd_hui.plusDays(1);
        LocalDate aprèsDemain = aujourd_hui.plusDays(2);

        Reservation reservation1 = new Reservation(aujourd_hui, LocalTime.of(8, 0), LocalTime.of(10, 0),
                "Cours de programmation Java", 24, enseignant1, salle1, ordinateur1, formation1);

        Reservation reservation2 = new Reservation(demain, LocalTime.of(14, 0), LocalTime.of(16, 0),
                "Conférence mathématiques", 120, enseignant2, salle2, projecteur1, formation2);

        Reservation reservation3 = new Reservation(aprèsDemain, LocalTime.of(10, 0), LocalTime.of(12, 0),
                "TP Physique", 16, enseignant3, salle4, null, formation3);

        Reservation reservation4 = new Reservation(demain, LocalTime.of(16, 30), LocalTime.of(18, 30),
                "Cours algorithmique", 30, enseignant1, salle3, projecteur2, formation1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
        log.info("📅 {} réservations créées", 4);

        // Afficher un résumé
        log.info("📊 RÉSUMÉ DES DONNÉES INITIALES:");
        log.info("   👥 Enseignants: {}", enseignantRepository.count());
        log.info("   🎓 Formations: {}", formationRepository.count());
        log.info("   🏢 Salles: {}", salleRepository.count());
        log.info("   💻 Matériels: {}", materielRepository.count());
        log.info("   📅 Réservations: {}", reservationRepository.count());
    }
}
