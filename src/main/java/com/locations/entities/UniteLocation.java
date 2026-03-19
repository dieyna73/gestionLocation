package com.locations.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unites_location")
public class UniteLocation {

    public enum Statut { DISPONIBLE, OCCUPEE, EN_TRAVAUX }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_unite", nullable = false, length = 20)
    private String numeroUnite;

    @Column(name = "nombre_pieces")
    private int nombrePieces;

    @Column(precision = 10, scale = 2)
    private BigDecimal superficie;

    @Column(name = "loyer_mensuel", nullable = false, precision = 10, scale = 2)
    private BigDecimal loyerMensuel;

    @Column(name = "charges_mensuelles", precision = 10, scale = 2)
    private BigDecimal chargesMensuelles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut = Statut.DISPONIBLE;

    @Column(length = 500)
    private String description;

    @Column(length = 300)
    private String equipements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "immeuble_id", nullable = false)
    private Immeuble immeuble;

    @OneToMany(mappedBy = "uniteLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContratLocation> contrats = new ArrayList<>();

    // Constructeurs
    public UniteLocation() {}

    public UniteLocation(String numeroUnite, int nombrePieces, BigDecimal superficie,
                         BigDecimal loyerMensuel, BigDecimal chargesMensuelles,
                         String description, String equipements, Immeuble immeuble) {
        this.numeroUnite = numeroUnite;
        this.nombrePieces = nombrePieces;
        this.superficie = superficie;
        this.loyerMensuel = loyerMensuel;
        this.chargesMensuelles = chargesMensuelles;
        this.description = description;
        this.equipements = equipements;
        this.immeuble = immeuble;
        this.statut = Statut.DISPONIBLE;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroUnite() { return numeroUnite; }
    public void setNumeroUnite(String numeroUnite) { this.numeroUnite = numeroUnite; }

    public int getNombrePieces() { return nombrePieces; }
    public void setNombrePieces(int nombrePieces) { this.nombrePieces = nombrePieces; }

    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }

    public BigDecimal getLoyerMensuel() { return loyerMensuel; }
    public void setLoyerMensuel(BigDecimal loyerMensuel) { this.loyerMensuel = loyerMensuel; }

    public BigDecimal getChargesMensuelles() { return chargesMensuelles; }
    public void setChargesMensuelles(BigDecimal chargesMensuelles) { this.chargesMensuelles = chargesMensuelles; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEquipements() { return equipements; }
    public void setEquipements(String equipements) { this.equipements = equipements; }

    public Immeuble getImmeuble() { return immeuble; }
    public void setImmeuble(Immeuble immeuble) { this.immeuble = immeuble; }

    public List<ContratLocation> getContrats() { return contrats; }
    public void setContrats(List<ContratLocation> contrats) { this.contrats = contrats; }
}
