package com.example.CSI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "enseignants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enseignant")
    private Long idEnseignant;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Column(name = "nom_enseignant", nullable = false)
    private String nomEnseignant;

    @NotBlank(message = "Le prénom ne peut pas être vide")
    @Column(name = "prenom_enseignant", nullable = false)
    private String prenomEnseignant;

    @Email(message = "Format d'email invalide")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "specialite")
    private String specialite;

    // Relations
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Formation> formationsResponsables;

    // Constructeur sans les relations
    public Enseignant(String nomEnseignant, String prenomEnseignant, String email, String telephone, String specialite) {
        this.nomEnseignant = nomEnseignant;
        this.prenomEnseignant = prenomEnseignant;
        this.email = email;
        this.telephone = telephone;
        this.specialite = specialite;
    }
}