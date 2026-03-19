package com.locations.services;

import com.locations.dao.UtilisateurDAO;
import com.locations.entities.Utilisateur;
import com.locations.utils.PasswordUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AuthService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur authentifier(String username, String motDePasse) {
        Optional<Utilisateur> opt = utilisateurDAO.findByUsername(username);
        if (opt.isEmpty()) throw new RuntimeException("Identifiants incorrects");

        Utilisateur user = opt.get();
        if (!user.isActif()) throw new RuntimeException("Compte désactivé");
        if (!PasswordUtil.verifyPassword(motDePasse, user.getMotDePasse()))
            throw new RuntimeException("Identifiants incorrects");

        user.setDerniereConnexion(LocalDateTime.now());
        utilisateurDAO.save(user);
        return user;
    }

    public Utilisateur creerAdmin(String username, String motDePasse, String email) {
        if (utilisateurDAO.findByUsername(username).isPresent())
            throw new IllegalArgumentException("Ce nom d'utilisateur existe déjà");
        Utilisateur admin = new Utilisateur(username, PasswordUtil.hashPassword(motDePasse),
                email, Utilisateur.Role.ADMIN);
        return utilisateurDAO.save(admin);
    }

    public Utilisateur changerMotDePasse(Long userId, String ancienMdp, String nouveauMdp) {
        Utilisateur user = utilisateurDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        if (!PasswordUtil.verifyPassword(ancienMdp, user.getMotDePasse()))
            throw new RuntimeException("Ancien mot de passe incorrect");
        user.setMotDePasse(PasswordUtil.hashPassword(nouveauMdp));
        return utilisateurDAO.save(user);
    }

    public Utilisateur toggleActif(Long userId) {
        Utilisateur user = utilisateurDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        user.setActif(!user.isActif());
        return utilisateurDAO.save(user);
    }

    public List<Utilisateur> listerTous() {
        return utilisateurDAO.findAll();
    }

    public void supprimerUtilisateur(Long id) {
        utilisateurDAO.delete(id);
    }

    public long compterUtilisateurs() {
        return utilisateurDAO.count();
    }
}
