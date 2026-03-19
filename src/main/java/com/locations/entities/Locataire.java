package com.locations.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locataires")
public class Locataire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "piece_identite", length = 100)
    private String pieceIdentite;

    @Column(name = "numero_piece", length = 50)
    private String numeroPiece;

    @Column(length = 300)
    private String adresse;

    @Column(length = 200)
    private String profession;

    @Column(name = "revenu_mensuel", precision = 10, scale = 2)
    private java.math.BigDecimal revenuMensuel;

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    @OneToOne(mappedBy = "locataire", cascade = CascadeType.ALL)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "locataire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContratLocation> contrats = new ArrayList<>();

    // Constructeurs
    public Locataire() {
        this.dateInscription = LocalDate.now();
    }

    public Locataire(String nom, String prenom, String email, String telephone,
                     LocalDate dateNaissance, String pieceIdentite, String numeroPiece,
                     String adresse, String profession) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.pieceIdentite = pieceIdentite;
        this.numeroPiece = numeroPiece;
        this.adresse = adresse;
        this.profession = profession;
        this.dateInscription = LocalDate.now();
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getPieceIdentite() { return pieceIdentite; }
    public void setPieceIdentite(String pieceIdentite) { this.pieceIdentite = pieceIdentite; }

    public String getNumeroPiece() { return numeroPiece; }
    public void setNumeroPiece(String numeroPiece) { this.numeroPiece = numeroPiece; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public java.math.BigDecimal getRevenuMensuel() { return revenuMensuel; }
    public void setRevenuMensuel(java.math.BigDecimal revenuMensuel) { this.revenuMensuel = revenuMensuel; }

    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) { this.dateInscription = dateInscription; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public List<ContratLocation> getContrats() { return contrats; }
    public void setContrats(List<ContratLocation> contrats) { this.contrats = contrats; }
}
