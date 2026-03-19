package com.locations.services;

import com.locations.dao.ContratLocationDAO;
import com.locations.dao.LocataireDAO;
import com.locations.dao.UniteLocationDAO;
import com.locations.entities.ContratLocation;
import com.locations.entities.Locataire;
import com.locations.entities.UniteLocation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ContratLocationService {

    private final ContratLocationDAO contratDAO = new ContratLocationDAO();
    private final UniteLocationDAO uniteDAO = new UniteLocationDAO();
    private final LocataireDAO locataireDAO = new LocataireDAO();

    public ContratLocation creerContrat(Long uniteId, Long locataireId,
                                         String dateDébutStr, String dateFinStr,
                                         BigDecimal loyerMensuel, BigDecimal depotGarantie,
                                         String conditions) {
        UniteLocation unite = uniteDAO.findById(uniteId)
                .orElseThrow(() -> new RuntimeException("Unité introuvable"));
        if (unite.getStatut() != UniteLocation.Statut.DISPONIBLE)
            throw new IllegalStateException("Cette unité n'est pas disponible");

        Locataire locataire = locataireDAO.findById(locataireId)
                .orElseThrow(() -> new RuntimeException("Locataire introuvable"));

        LocalDate dateDebut = LocalDate.parse(dateDébutStr);
        LocalDate dateFin = (dateFinStr != null && !dateFinStr.isEmpty()) ? LocalDate.parse(dateFinStr) : null;

        String numeroContrat = genererNumeroContrat();

        ContratLocation contrat = new ContratLocation(numeroContrat, dateDebut, dateFin,
                loyerMensuel, depotGarantie, unite, locataire);
        contrat.setConditions(conditions);
        contrat.setStatut(ContratLocation.Statut.ACTIF);

        unite.setStatut(UniteLocation.Statut.OCCUPEE);
        uniteDAO.save(unite);

        return contratDAO.save(contrat);
    }

    public ContratLocation modifierStatut(Long contratId, ContratLocation.Statut statut) {
        ContratLocation contrat = contratDAO.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable"));
        contrat.setStatut(statut);

        if (statut == ContratLocation.Statut.RESILIE || statut == ContratLocation.Statut.EXPIRE) {
            UniteLocation unite = contrat.getUniteLocation();
            unite.setStatut(UniteLocation.Statut.DISPONIBLE);
            uniteDAO.save(unite);
        }

        return contratDAO.save(contrat);
    }

    public void supprimerContrat(Long id) {
        ContratLocation contrat = contratDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable"));
        if (contrat.getStatut() == ContratLocation.Statut.ACTIF)
            throw new IllegalStateException("Impossible de supprimer un contrat actif");
        contratDAO.delete(id);
    }

    public Optional<ContratLocation> trouverParId(Long id) {
        return contratDAO.findById(id);
    }

    public List<ContratLocation> listerTous() {
        return contratDAO.findAll();
    }

    public List<ContratLocation> listerActifs() {
        return contratDAO.findActifs();
    }

    public List<ContratLocation> listerParLocataire(Long locataireId) {
        return contratDAO.findByLocataire(locataireId);
    }

    public long compterParStatut(String statut) {
        return contratDAO.countByStatut(statut);
    }

    private String genererNumeroContrat() {
        String annee = String.valueOf(LocalDate.now().getYear());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "CTR-" + annee + "-" + uuid;
    }
}
