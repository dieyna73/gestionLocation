package com.locations.dao;

import com.locations.entities.Immeuble;
import com.locations.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ImmeubleDAO {

    public Immeuble save(Immeuble immeuble) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (immeuble.getId() == null) {
                em.persist(immeuble);
            } else {
                immeuble = em.merge(immeuble);
            }
            em.getTransaction().commit();
            return immeuble;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur sauvegarde immeuble", e);
        } finally {
            em.close();
        }
    }

    public Optional<Immeuble> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Immeuble.class, id));
        } finally {
            em.close();
        }
    }

    public List<Immeuble> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Immeuble> query = em.createQuery(
                "SELECT i FROM Immeuble i ORDER BY i.nom", Immeuble.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Immeuble> findByVille(String ville) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Immeuble> query = em.createQuery(
                "SELECT i FROM Immeuble i WHERE LOWER(i.ville) LIKE LOWER(:ville) ORDER BY i.nom",
                Immeuble.class);
            query.setParameter("ville", "%" + ville + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Immeuble> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Immeuble> query = em.createQuery(
                "SELECT i FROM Immeuble i WHERE LOWER(i.nom) LIKE LOWER(:kw) " +
                "OR LOWER(i.adresse) LIKE LOWER(:kw) OR LOWER(i.ville) LIKE LOWER(:kw) " +
                "ORDER BY i.nom", Immeuble.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Immeuble immeuble = em.find(Immeuble.class, id);
            if (immeuble != null) {
                em.remove(immeuble);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur suppression immeuble", e);
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(i) FROM Immeuble i", Long.class)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }
}
