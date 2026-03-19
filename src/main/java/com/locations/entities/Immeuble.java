package com.locations.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "immeubles")
public class Immeuble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nom;

    @Column(nullable = false, length = 300)
    private String adresse;

    @Column(length = 100)
    private String ville;

    @Column(length = 20)
    private String codePostal;

    @Column(name = "nombre_unites")
    private int nombreUnites;

    @Column(length = 500)
    private String description;

    @Column(length = 300)
    private String equipements;

    @Column(name = "annee_construction")
    private int anneeConstruction;

    @Column(length = 200)
    private String proprietaire;

    @OneToMany(mappedBy = "immeuble", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UniteLocation> unites = new ArrayList<>();

    // Constructeurs
    public Immeuble() {}

    public Immeuble(String nom, String adresse, String ville, String codePostal,
                    int nombreUnites, String description, String equipements,
                    int anneeConstruction, String proprietaire) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.nombreUnites = nombreUnites;
        this.description = description;
        this.equipements = equipements;
        this.anneeConstruction = anneeConstruction;
        this.proprietaire = proprietaire;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

    public int getNombreUnites() { return nombreUnites; }
    public void setNombreUnites(int nombreUnites) { this.nombreUnites = nombreUnites; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEquipements() { return equipements; }
    public void setEquipements(String equipements) { this.equipements = equipements; }

    public int getAnneeConstruction() { return anneeConstruction; }
    public void setAnneeConstruction(int anneeConstruction) { this.anneeConstruction = anneeConstruction; }

    public String getProprietaire() { return proprietaire; }
    public void setProprietaire(String proprietaire) { this.proprietaire = proprietaire; }

    public List<UniteLocation> getUnites() { return unites; }
    public void setUnites(List<UniteLocation> unites) { this.unites = unites; }

    @Override
    public String toString() {
        return "Immeuble{id=" + id + ", nom='" + nom + "', adresse='" + adresse + "'}";
    }
}
