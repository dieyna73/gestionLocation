package com.locations.services;

import com.locations.dao.ImmeubleDAO;
import com.locations.dao.UniteLocationDAO;
import com.locations.entities.Immeuble;
import com.locations.entities.UniteLocation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UniteLocationService {

    private final UniteLocationDAO uniteDAO = new UniteLocationDAO();
    private final ImmeubleDAO immeubleDAO = new ImmeubleDAO();

    public UniteLocation creerUnite(Long immeubleId, String numeroUnite, int nombrePieces,
                                     BigDecimal superficie, BigDecimal loyerMensuel,
                                     BigDecimal chargesMensuelles, String description,
                                     String equipements) {
        Immeuble immeuble = immeubleDAO.findById(immeubleId)
                .orElseThrow(() -> new RuntimeException("Immeuble introuvable"));
        if (numeroUnite == null || numeroUnite.trim().isEmpty())
            throw new IllegalArgumentException("Le numéro d'unité est obligatoire");

        UniteLocation unite = new UniteLocation(numeroUnite, nombrePieces, superficie,
                loyerMensuel, chargesMensuelles, description, equipements, immeuble);
        return uniteDAO.save(unite);
    }

    public UniteLocation modifierUnite(Long id, String numeroUnite, int nombrePieces,
                                        BigDecimal superficie, BigDecimal loyerMensuel,
                                        BigDecimal chargesMensuelles, String description,
                                        String equipements, String statut) {
        UniteLocation unite = uniteDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Unité introuvable"));
        unite.setNumeroUnite(numeroUnite);
        unite.setNombrePieces(nombrePieces);
        unite.setSuperficie(superficie);
        unite.setLoyerMensuel(loyerMensuel);
        unite.setChargesMensuelles(chargesMensuelles);
        unite.setDescription(description);
        unite.setEquipements(equipements);
        if (statut != null) unite.setStatut(UniteLocation.Statut.valueOf(statut));
        return uniteDAO.save(unite);
    }

    public void supprimerUnite(Long id) {
        uniteDAO.findById(id).orElseThrow(() -> new RuntimeException("Unité introuvable"));
        uniteDAO.delete(id);
    }

    public Optional<UniteLocation> trouverParId(Long id) {
        return uniteDAO.findById(id);
    }

    public List<UniteLocation> listerTous() {
        return uniteDAO.findAll();
    }

    public List<UniteLocation> listerDisponibles() {
        return uniteDAO.findDisponibles();
    }

    public List<UniteLocation> listerDisponiblesAvecFiltres(Integer pieces, BigDecimal loyerMax) {
        return uniteDAO.findDisponiblesWithFilters(pieces, loyerMax);
    }

    public List<UniteLocation> listerParImmeuble(Long immeubleId) {
        return uniteDAO.findByImmeuble(immeubleId);
    }

    public long compterParStatut(String statut) {
        return uniteDAO.countByStatut(statut);
    }
}
