package com.locations.services;

import com.locations.dao.ContratLocationDAO;
import com.locations.dao.PaiementDAO;
import com.locations.entities.ContratLocation;
import com.locations.entities.Paiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PaiementService {

    private final PaiementDAO paiementDAO = new PaiementDAO();
    private final ContratLocationDAO contratDAO = new ContratLocationDAO();

    public Paiement enregistrerPaiement(Long contratId, BigDecimal montant,
                                         String dateEcheanceStr, String periodeMois,
                                         String modePaiementStr, String commentaire) {
        ContratLocation contrat = contratDAO.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable"));

        LocalDate dateEcheance = LocalDate.parse(dateEcheanceStr);

        Paiement paiement = new Paiement(montant, dateEcheance, periodeMois, contrat);
        paiement.setDatePaiement(LocalDate.now());
        paiement.setStatut(Paiement.Statut.PAYE);
        paiement.setCommentaire(commentaire);
        paiement.setNumeroRecu(genererNumeroRecu());

        if (modePaiementStr != null && !modePaiementStr.isEmpty()) {
            paiement.setModePaiement(Paiement.ModePaiement.valueOf(modePaiementStr));
        }

        return paiementDAO.save(paiement);
    }

    public Paiement creerEcheance(Long contratId, BigDecimal montant,
                                   String dateEcheanceStr, String periodeMois) {
        ContratLocation contrat = contratDAO.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable"));

        LocalDate dateEcheance = LocalDate.parse(dateEcheanceStr);
        Paiement paiement = new Paiement(montant, dateEcheance, periodeMois, contrat);
        paiement.setStatut(Paiement.Statut.EN_ATTENTE);

        return paiementDAO.save(paiement);
    }

    public Paiement marquerEnRetard(Long paiementId) {
        Paiement p = paiementDAO.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));
        p.setStatut(Paiement.Statut.EN_RETARD);
        return paiementDAO.save(p);
    }

    public Optional<Paiement> trouverParId(Long id) {
        return paiementDAO.findById(id);
    }

    public List<Paiement> listerTous() {
        return paiementDAO.findAll();
    }

    public List<Paiement> listerParContrat(Long contratId) {
        return paiementDAO.findByContrat(contratId);
    }

    public List<Paiement> listerEnRetard() {
        return paiementDAO.findEnRetard();
    }

    public BigDecimal totalEncaisse() {
        return paiementDAO.sumMontantPaye();
    }

    private String genererNumeroRecu() {
        return "REC-" + LocalDate.now().getYear() + "-"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
