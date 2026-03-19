package com.locations.dao;

import com.locations.entities.ContratLocation;
import com.locations.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ContratLocationDAO {

    public ContratLocation save(ContratLocation contrat) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (contrat.getId() == null) {
                em.persist(contrat);
            } else {
                contrat = em.merge(contrat);
            }
            em.getTransaction().commit();
            return contrat;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur sauvegarde contrat", e);
        } finally {
            em.close();
        }
    }

    public Optional<ContratLocation> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(ContratLocation.class, id));
        } finally {
            em.close();
        }
    }

    public Optional<ContratLocation> findByNumero(String numero) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<ContratLocation> q = em.createQuery(
                "SELECT c FROM ContratLocation c WHERE c.numeroContrat = :num", ContratLocation.class);
            q.setParameter("num", numero);
            return Optional.of(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public List<ContratLocation> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT c FROM ContratLocation c JOIN FETCH c.locataire JOIN FETCH c.uniteLocation " +
                "ORDER BY c.dateCreation DESC", ContratLocation.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<ContratLocation> findByLocataire(Long locataireId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<ContratLocation> q = em.createQuery(
                "SELECT c FROM ContratLocation c WHERE c.locataire.id = :lid ORDER BY c.dateCreation DESC",
                ContratLocation.class);
            q.setParameter("lid", locataireId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<ContratLocation> findActifs() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT c FROM ContratLocation c JOIN FETCH c.locataire JOIN FETCH c.uniteLocation " +
                "WHERE c.statut = 'ACTIF' ORDER BY c.dateDebut", ContratLocation.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ContratLocation c = em.find(ContratLocation.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur suppression contrat", e);
        } finally {
            em.close();
        }
    }

    public long countByStatut(String statut) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(c) FROM ContratLocation c WHERE c.statut = :s", Long.class)
                     .setParameter("s", ContratLocation.Statut.valueOf(statut))
                     .getSingleResult();
        } finally {
            em.close();
        }
    }
}
