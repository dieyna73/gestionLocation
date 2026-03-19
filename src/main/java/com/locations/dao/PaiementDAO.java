package com.locations.dao;

import com.locations.entities.Paiement;
import com.locations.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PaiementDAO {

    public Paiement save(Paiement paiement) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (paiement.getId() == null) {
                em.persist(paiement);
            } else {
                paiement = em.merge(paiement);
            }
            em.getTransaction().commit();
            return paiement;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur sauvegarde paiement", e);
        } finally {
            em.close();
        }
    }

    public Optional<Paiement> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Paiement.class, id));
        } finally {
            em.close();
        }
    }

    public List<Paiement> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT p FROM Paiement p JOIN FETCH p.contrat ORDER BY p.dateEcheance DESC",
                Paiement.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Paiement> findByContrat(Long contratId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Paiement> q = em.createQuery(
                "SELECT p FROM Paiement p WHERE p.contrat.id = :cid ORDER BY p.dateEcheance DESC",
                Paiement.class);
            q.setParameter("cid", contratId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Paiement> findEnRetard() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT p FROM Paiement p JOIN FETCH p.contrat WHERE p.statut = 'EN_RETARD' " +
                "ORDER BY p.dateEcheance", Paiement.class).getResultList();
        } finally {
            em.close();
        }
    }

    public BigDecimal sumMontantPaye() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            BigDecimal result = em.createQuery(
                "SELECT SUM(p.montant) FROM Paiement p WHERE p.statut = 'PAYE'", BigDecimal.class)
                .getSingleResult();
            return result != null ? result : BigDecimal.ZERO;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Paiement p = em.find(Paiement.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur suppression paiement", e);
        } finally {
            em.close();
        }
    }
}
