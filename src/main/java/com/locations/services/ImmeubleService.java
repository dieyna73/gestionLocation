package com.locations.services;

import com.locations.dao.ImmeubleDAO;
import com.locations.entities.Immeuble;

import java.util.List;
import java.util.Optional;

public class ImmeubleService {

    private final ImmeubleDAO immeubleDAO = new ImmeubleDAO();

    public Immeuble creerImmeuble(String nom, String adresse, String ville, String codePostal,
                                   int nombreUnites, String description, String equipements,
                                   int anneeConstruction, String proprietaire) {
        if (nom == null || nom.trim().isEmpty()) throw new IllegalArgumentException("Le nom est obligatoire");
        if (adresse == null || adresse.trim().isEmpty()) throw new IllegalArgumentException("L'adresse est obligatoire");

        Immeuble immeuble = new Immeuble(nom.trim(), adresse.trim(), ville, codePostal,
                nombreUnites, description, equipements, anneeConstruction, proprietaire);
        return immeubleDAO.save(immeuble);
    }

    public Immeuble modifierImmeuble(Long id, String nom, String adresse, String ville,
                                      String codePostal, int nombreUnites, String description,
                                      String equipements, int anneeConstruction, String proprietaire) {
        Immeuble immeuble = immeubleDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Immeuble introuvable avec l'id: " + id));
        immeuble.setNom(nom.trim());
        immeuble.setAdresse(adresse.trim());
        immeuble.setVille(ville);
        immeuble.setCodePostal(codePostal);
        immeuble.setNombreUnites(nombreUnites);
        immeuble.setDescription(description);
        immeuble.setEquipements(equipements);
        immeuble.setAnneeConstruction(anneeConstruction);
        immeuble.setProprietaire(proprietaire);
        return immeubleDAO.save(immeuble);
    }

    public void supprimerImmeuble(Long id) {
        immeubleDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Immeuble introuvable avec l'id: " + id));
        immeubleDAO.delete(id);
    }

    public Optional<Immeuble> trouverParId(Long id) {
        return immeubleDAO.findById(id);
    }

    public List<Immeuble> listerTous() {
        return immeubleDAO.findAll();
    }

    public List<Immeuble> rechercher(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return listerTous();
        return immeubleDAO.search(keyword.trim());
    }

    public long compterImmeubles() {
        return immeubleDAO.count();
    }
}
