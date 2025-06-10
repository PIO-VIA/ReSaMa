package com.example.CSI.mapper;

import com.example.CSI.dto.*;
import com.example.CSI.model.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOMapper {

    // Enseignant mappings
    public EnseignantDTO toEnseignantDTO(Enseignant enseignant) {
        if (enseignant == null) return null;

        EnseignantDTO dto = new EnseignantDTO();
        dto.setIdEnseignant(enseignant.getIdEnseignant());
        dto.setNomEnseignant(enseignant.getNomEnseignant());
        dto.setPrenomEnseignant(enseignant.getPrenomEnseignant());
        dto.setEmail(enseignant.getEmail());
        dto.setTelephone(enseignant.getTelephone());
        dto.setSpecialite(enseignant.getSpecialite());

        // Mapper les formations responsables
        if (enseignant.getFormationsResponsables() != null) {
            dto.setFormationsResponsables(
                    enseignant.getFormationsResponsables().stream()
                            .map(this::toFormationSimpleDTO)
                            .collect(Collectors.toList())
            );
        }

        // Mapper les réservations
        if (enseignant.getReservations() != null) {
            dto.setReservations(
                    enseignant.getReservations().stream()
                            .map(this::toReservationSimpleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public EnseignantSimpleDTO toEnseignantSimpleDTO(Enseignant enseignant) {
        if (enseignant == null) return null;

        EnseignantSimpleDTO dto = new EnseignantSimpleDTO();
        dto.setIdEnseignant(enseignant.getIdEnseignant());
        dto.setNomEnseignant(enseignant.getNomEnseignant());
        dto.setPrenomEnseignant(enseignant.getPrenomEnseignant());
        dto.setEmail(enseignant.getEmail());
        dto.setTelephone(enseignant.getTelephone());
        dto.setSpecialite(enseignant.getSpecialite());

        return dto;
    }

    public List<EnseignantDTO> toEnseignantDTOList(List<Enseignant> enseignants) {
        if (enseignants == null) return Collections.emptyList();
        return enseignants.stream()
                .map(this::toEnseignantDTO)
                .collect(Collectors.toList());
    }

    public List<EnseignantSimpleDTO> toEnseignantSimpleDTOList(List<Enseignant> enseignants) {
        if (enseignants == null) return Collections.emptyList();
        return enseignants.stream()
                .map(this::toEnseignantSimpleDTO)
                .collect(Collectors.toList());
    }

    // Formation mappings
    public FormationDTO toFormationDTO(Formation formation) {
        if (formation == null) return null;

        FormationDTO dto = new FormationDTO();
        dto.setIdFormation(formation.getIdFormation());
        dto.setCodeFormation(formation.getCodeFormation());
        dto.setNomFormation(formation.getNomFormation());
        dto.setDescription(formation.getDescription());
        dto.setNiveau(formation.getNiveau());
        dto.setDureeHeures(formation.getDureeHeures());
        dto.setResponsable(toEnseignantSimpleDTO(formation.getResponsable()));

        // Mapper les réservations
        if (formation.getReservations() != null) {
            dto.setReservations(
                    formation.getReservations().stream()
                            .map(this::toReservationSimpleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public FormationSimpleDTO toFormationSimpleDTO(Formation formation) {
        if (formation == null) return null;

        FormationSimpleDTO dto = new FormationSimpleDTO();
        dto.setIdFormation(formation.getIdFormation());
        dto.setCodeFormation(formation.getCodeFormation());
        dto.setNomFormation(formation.getNomFormation());
        dto.setDescription(formation.getDescription());
        dto.setNiveau(formation.getNiveau());
        dto.setDureeHeures(formation.getDureeHeures());
        dto.setResponsable(toEnseignantSimpleDTO(formation.getResponsable()));

        return dto;
    }

    public List<FormationDTO> toFormationDTOList(List<Formation> formations) {
        if (formations == null) return Collections.emptyList();
        return formations.stream()
                .map(this::toFormationDTO)
                .collect(Collectors.toList());
    }

    public List<FormationSimpleDTO> toFormationSimpleDTOList(List<Formation> formations) {
        if (formations == null) return Collections.emptyList();
        return formations.stream()
                .map(this::toFormationSimpleDTO)
                .collect(Collectors.toList());
    }

    // Salle mappings
    public SalleDTO toSalleDTO(Salle salle) {
        if (salle == null) return null;

        SalleDTO dto = new SalleDTO();
        dto.setCodeSalle(salle.getCodeSalle());
        dto.setNomSalle(salle.getNomSalle());
        dto.setCapacite(salle.getCapacite());
        dto.setDisponibilite(salle.getDisponibilite());
        dto.setTypeSalle(salle.getTypeSalle());
        dto.setBatiment(salle.getBatiment());
        dto.setEtage(salle.getEtage());
        dto.setEquipements(salle.getEquipements());

        // Mapper les réservations
        if (salle.getReservations() != null) {
            dto.setReservations(
                    salle.getReservations().stream()
                            .map(this::toReservationSimpleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public SalleSimpleDTO toSalleSimpleDTO(Salle salle) {
        if (salle == null) return null;

        SalleSimpleDTO dto = new SalleSimpleDTO();
        dto.setCodeSalle(salle.getCodeSalle());
        dto.setNomSalle(salle.getNomSalle());
        dto.setCapacite(salle.getCapacite());
        dto.setDisponibilite(salle.getDisponibilite());
        dto.setTypeSalle(salle.getTypeSalle());
        dto.setBatiment(salle.getBatiment());
        dto.setEtage(salle.getEtage());
        dto.setEquipements(salle.getEquipements());

        return dto;
    }

    public List<SalleDTO> toSalleDTOList(List<Salle> salles) {
        if (salles == null) return Collections.emptyList();
        return salles.stream()
                .map(this::toSalleDTO)
                .collect(Collectors.toList());
    }

    public List<SalleSimpleDTO> toSalleSimpleDTOList(List<Salle> salles) {
        if (salles == null) return Collections.emptyList();
        return salles.stream()
                .map(this::toSalleSimpleDTO)
                .collect(Collectors.toList());
    }

    // Materiel mappings
    public MaterielDTO toMaterielDTO(Materiel materiel) {
        if (materiel == null) return null;

        if (materiel instanceof Ordinateur) {
            return toOrdinateurDTO((Ordinateur) materiel);
        } else if (materiel instanceof VideoProjecteur) {
            return toVideoProjecteurDTO((VideoProjecteur) materiel);
        }

        return null;
    }

    public MaterielSimpleDTO toMaterielSimpleDTO(Materiel materiel) {
        if (materiel == null) return null;

        if (materiel instanceof Ordinateur) {
            return toOrdinateurSimpleDTO((Ordinateur) materiel);
        } else if (materiel instanceof VideoProjecteur) {
            return toVideoProjecteurSimpleDTO((VideoProjecteur) materiel);
        }

        return null;
    }

    public OrdinateurDTO toOrdinateurDTO(Ordinateur ordinateur) {
        if (ordinateur == null) return null;

        OrdinateurDTO dto = new OrdinateurDTO();
        dto.setCodeMateriel(ordinateur.getCodeMateriel());
        dto.setDisponibilite(ordinateur.getDisponibilite());
        dto.setMarque(ordinateur.getMarque());
        dto.setModele(ordinateur.getModele());
        dto.setEtat(ordinateur.getEtat());
        dto.setDateAcquisition(ordinateur.getDateAcquisition());
        dto.setLocalisation(ordinateur.getLocalisation());
        dto.setProcesseur(ordinateur.getProcesseur());
        dto.setRam(ordinateur.getRam());
        dto.setStockage(ordinateur.getStockage());
        dto.setTailleEcran(ordinateur.getTailleEcran());
        dto.setSystemeExploitation(ordinateur.getSystemeExploitation());
        dto.setTypeOrdinateur(ordinateur.getTypeOrdinateur());

        // Mapper les réservations
        if (ordinateur.getReservations() != null) {
            dto.setReservations(
                    ordinateur.getReservations().stream()
                            .map(this::toReservationSimpleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public OrdinateurSimpleDTO toOrdinateurSimpleDTO(Ordinateur ordinateur) {
        if (ordinateur == null) return null;

        OrdinateurSimpleDTO dto = new OrdinateurSimpleDTO();
        dto.setCodeMateriel(ordinateur.getCodeMateriel());
        dto.setDisponibilite(ordinateur.getDisponibilite());
        dto.setMarque(ordinateur.getMarque());
        dto.setModele(ordinateur.getModele());
        dto.setEtat(ordinateur.getEtat());
        dto.setDateAcquisition(ordinateur.getDateAcquisition());
        dto.setLocalisation(ordinateur.getLocalisation());
        dto.setProcesseur(ordinateur.getProcesseur());
        dto.setRam(ordinateur.getRam());
        dto.setStockage(ordinateur.getStockage());
        dto.setTailleEcran(ordinateur.getTailleEcran());
        dto.setSystemeExploitation(ordinateur.getSystemeExploitation());
        dto.setTypeOrdinateur(ordinateur.getTypeOrdinateur());

        return dto;
    }

    public VideoProjecteurDTO toVideoProjecteurDTO(VideoProjecteur videoProjecteur) {
        if (videoProjecteur == null) return null;

        VideoProjecteurDTO dto = new VideoProjecteurDTO();
        dto.setCodeMateriel(videoProjecteur.getCodeMateriel());
        dto.setDisponibilite(videoProjecteur.getDisponibilite());
        dto.setMarque(videoProjecteur.getMarque());
        dto.setModele(videoProjecteur.getModele());
        dto.setEtat(videoProjecteur.getEtat());
        dto.setDateAcquisition(videoProjecteur.getDateAcquisition());
        dto.setLocalisation(videoProjecteur.getLocalisation());
        dto.setDescription(videoProjecteur.getDescription());
        dto.setResolution(videoProjecteur.getResolution());
        dto.setLuminosite(videoProjecteur.getLuminosite());
        dto.setConnectivite(videoProjecteur.getConnectivite());
        dto.setPoids(videoProjecteur.getPoids());
        dto.setTypeProjection(videoProjecteur.getTypeProjection());

        // Mapper les réservations
        if (videoProjecteur.getReservations() != null) {
            dto.setReservations(
                    videoProjecteur.getReservations().stream()
                            .map(this::toReservationSimpleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public VideoProjecteurSimpleDTO toVideoProjecteurSimpleDTO(VideoProjecteur videoProjecteur) {
        if (videoProjecteur == null) return null;

        VideoProjecteurSimpleDTO dto = new VideoProjecteurSimpleDTO();
        dto.setCodeMateriel(videoProjecteur.getCodeMateriel());
        dto.setDisponibilite(videoProjecteur.getDisponibilite());
        dto.setMarque(videoProjecteur.getMarque());
        dto.setModele(videoProjecteur.getModele());
        dto.setEtat(videoProjecteur.getEtat());
        dto.setDateAcquisition(videoProjecteur.getDateAcquisition());
        dto.setLocalisation(videoProjecteur.getLocalisation());
        dto.setDescription(videoProjecteur.getDescription());
        dto.setResolution(videoProjecteur.getResolution());
        dto.setLuminosite(videoProjecteur.getLuminosite());
        dto.setConnectivite(videoProjecteur.getConnectivite());
        dto.setPoids(videoProjecteur.getPoids());
        dto.setTypeProjection(videoProjecteur.getTypeProjection());

        return dto;
    }

    public List<MaterielDTO> toMaterielDTOList(List<Materiel> materiels) {
        if (materiels == null) return Collections.emptyList();
        return materiels.stream()
                .map(this::toMaterielDTO)
                .collect(Collectors.toList());
    }

    public List<MaterielSimpleDTO> toMaterielSimpleDTOList(List<Materiel> materiels) {
        if (materiels == null) return Collections.emptyList();
        return materiels.stream()
                .map(this::toMaterielSimpleDTO)
                .collect(Collectors.toList());
    }

    // Reservation mappings
    public ReservationDTO toReservationDTO(Reservation reservation) {
        if (reservation == null) return null;

        ReservationDTO dto = new ReservationDTO();
        dto.setNumero(reservation.getNumero());
        dto.setJour(reservation.getJour());
        dto.setHeureDebut(reservation.getHeureDebut());
        dto.setHeureFin(reservation.getHeureFin());
        dto.setMotif(reservation.getMotif());
        dto.setStatut(reservation.getStatut() != null ? reservation.getStatut().toString() : null);
        dto.setNombreParticipants(reservation.getNombreParticipants());
        dto.setEnseignant(toEnseignantSimpleDTO(reservation.getEnseignant()));
        dto.setSalle(toSalleSimpleDTO(reservation.getSalle()));
        dto.setMateriel(toMaterielSimpleDTO(reservation.getMateriel()));
        dto.setFormation(toFormationSimpleDTO(reservation.getFormation()));

        return dto;
    }

    public ReservationSimpleDTO toReservationSimpleDTO(Reservation reservation) {
        if (reservation == null) return null;

        ReservationSimpleDTO dto = new ReservationSimpleDTO();
        dto.setNumero(reservation.getNumero());
        dto.setJour(reservation.getJour());
        dto.setHeureDebut(reservation.getHeureDebut());
        dto.setHeureFin(reservation.getHeureFin());
        dto.setMotif(reservation.getMotif());
        dto.setStatut(reservation.getStatut() != null ? reservation.getStatut().toString() : null);
        dto.setNombreParticipants(reservation.getNombreParticipants());

        return dto;
    }

    public List<ReservationDTO> toReservationDTOList(List<Reservation> reservations) {
        if (reservations == null) return Collections.emptyList();
        return reservations.stream()
                .map(this::toReservationDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationSimpleDTO> toReservationSimpleDTOList(List<Reservation> reservations) {
        if (reservations == null) return Collections.emptyList();
        return reservations.stream()
                .map(this::toReservationSimpleDTO)
                .collect(Collectors.toList());
    }
}