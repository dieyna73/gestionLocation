package com.locations.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrats_location")
public class ContratLocation {

    public enum Statut { EN_ATTENTE, ACTIF, EXPIRE, RESILIE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_contrat", nullable = false, unique = true, length = 50)
    private String numeroContrat;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "loyer_mensuel", nullable = false, precision = 10, scale = 2)
    private BigDecimal loyerMensuel;

    @Column(name = "depot_garantie", precision = 10, scale = 2)
    private BigDecimal depotGarantie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut = Statut.EN_ATTENTE;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(length = 1000)
    private String conditions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unite_location_id", nullable = false)
    private UniteLocation uniteLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locataire_id", nullable = false)
    private Locataire locataire;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> paiements = new ArrayList<>();

    // Constructeurs
    public ContratLocation() {
        this.dateCreation = LocalDate.now();
        this.statut = Statut.EN_ATTENTE;
    }

    public ContratLocation(String numeroContrat, LocalDate dateDebut, LocalDate dateFin,
                            BigDecimal loyerMensuel, BigDecimal depotGarantie,
                            UniteLocation uniteLocation, Locataire locataire) {
        this.numeroContrat = numeroContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.loyerMensuel = loyerMensuel;
        this.depotGarantie = depotGarantie;
        this.uniteLocation = uniteLocation;
        this.locataire = locataire;
        this.dateCreation = LocalDate.now();
        this.statut = Statut.EN_ATTENTE;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroContrat() { return numeroContrat; }
    public void setNumeroContrat(String numeroContrat) { this.numeroContrat = numeroContrat; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public BigDecimal getLoyerMensuel() { return loyerMensuel; }
    public void setLoyerMensuel(BigDecimal loyerMensuel) { this.loyerMensuel = loyerMensuel; }

    public BigDecimal getDepotGarantie() { return depotGarantie; }
    public void setDepotGarantie(BigDecimal depotGarantie) { this.depotGarantie = depotGarantie; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public String getConditions() { return conditions; }
    public void setConditions(String conditions) { this.conditions = conditions; }

    public UniteLocation getUniteLocation() { return uniteLocation; }
    public void setUniteLocation(UniteLocation uniteLocation) { this.uniteLocation = uniteLocation; }

    public Locataire getLocataire() { return locataire; }
    public void setLocataire(Locataire locataire) { this.locataire = locataire; }

    public List<Paiement> getPaiements() { return paiements; }
    public void setPaiements(List<Paiement> paiements) { this.paiements = paiements; }
}
