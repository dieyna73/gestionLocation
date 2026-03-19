package com.locations.dao;

import com.locations.entities.Locataire;
import com.locations.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class LocataireDAO {

    public Locataire save(Locataire locataire) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (locataire.getId() == null) {
                em.persist(locataire);
            } else {
                locataire = em.merge(locataire);
            }
            em.getTransaction().commit();
            return locataire;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur sauvegarde locataire", e);
        } finally {
            em.close();
        }
    }

    public Optional<Locataire> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Locataire.class, id));
        } finally {
            em.close();
        }
    }

    public Optional<Locataire> findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Locataire> q = em.createQuery(
                "SELECT l FROM Locataire l WHERE l.email = :email", Locataire.class);
            q.setParameter("email", email);
            return Optional.of(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public List<Locataire> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Locataire l ORDER BY l.nom, l.prenom",
                    Locataire.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Locataire> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Locataire> q = em.createQuery(
                "SELECT l FROM Locataire l WHERE LOWER(l.nom) LIKE LOWER(:kw) " +
                "OR LOWER(l.prenom) LIKE LOWER(:kw) OR LOWER(l.email) LIKE LOWER(:kw) " +
                "ORDER BY l.nom", Locataire.class);
            q.setParameter("kw", "%" + keyword + "%");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Locataire l = em.find(Locataire.class, id);
            if (l != null) em.remove(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur suppression locataire", e);
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(l) FROM Locataire l", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
