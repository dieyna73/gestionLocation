package com.locations.services;

import com.locations.dao.LocataireDAO;
import com.locations.dao.UtilisateurDAO;
import com.locations.entities.Locataire;
import com.locations.entities.Utilisateur;
import com.locations.utils.PasswordUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LocataireService {

    private final LocataireDAO locataireDAO = new LocataireDAO();
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Locataire inscrireLocataire(String nom, String prenom, String email, String telephone,
                                        String dateNaissanceStr, String pieceIdentite,
                                        String numeroPiece, String adresse, String profession,
                                        String username, String motDePasse) {
        if (locataireDAO.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Un locataire avec cet email existe déjà");
        if (utilisateurDAO.findByUsername(username).isPresent())
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà pris");

        LocalDate dateNaissance = (dateNaissanceStr != null && !dateNaissanceStr.isEmpty())
                ? LocalDate.parse(dateNaissanceStr) : null;

        Locataire locataire = new Locataire(nom, prenom, email, telephone, dateNaissance,
                pieceIdentite, numeroPiece, adresse, profession);
        locataireDAO.save(locataire);

        Utilisateur utilisateur = new Utilisateur(username, PasswordUtil.hashPassword(motDePasse),
                email, Utilisateur.Role.LOCATAIRE);
        utilisateur.setLocataire(locataire);
        utilisateurDAO.save(utilisateur);

        return locataire;
    }

    public Locataire modifierLocataire(Long id, String nom, String prenom, String email,
                                        String telephone, String dateNaissanceStr,
                                        String pieceIdentite, String numeroPiece,
                                        String adresse, String profession) {
        Locataire locataire = locataireDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Locataire introuvable"));

        locataire.setNom(nom);
        locataire.setPrenom(prenom);
        locataire.setEmail(email);
        locataire.setTelephone(telephone);
        if (dateNaissanceStr != null && !dateNaissanceStr.isEmpty())
            locataire.setDateNaissance(LocalDate.parse(dateNaissanceStr));
        locataire.setPieceIdentite(pieceIdentite);
        locataire.setNumeroPiece(numeroPiece);
        locataire.setAdresse(adresse);
        locataire.setProfession(profession);

        return locataireDAO.save(locataire);
    }

    public void supprimerLocataire(Long id) {
        locataireDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Locataire introuvable"));
        locataireDAO.delete(id);
    }

    public Optional<Locataire> trouverParId(Long id) {
        return locataireDAO.findById(id);
    }

    public Optional<Locataire> trouverParEmail(String email) {
        return locataireDAO.findByEmail(email);
    }

    public List<Locataire> listerTous() {
        return locataireDAO.findAll();
    }

    public List<Locataire> rechercher(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return listerTous();
        return locataireDAO.search(keyword);
    }

    public long compterLocataires() {
        return locataireDAO.count();
    }
}
