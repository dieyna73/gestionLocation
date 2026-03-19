package com.locations.dao;

import com.locations.entities.UniteLocation;
import com.locations.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UniteLocationDAO {

    public UniteLocation save(UniteLocation unite) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (unite.getId() == null) {
                em.persist(unite);
            } else {
                unite = em.merge(unite);
            }
            em.getTransaction().commit();
            return unite;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur sauvegarde unité", e);
        } finally {
            em.close();
        }
    }

    public Optional<UniteLocation> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(UniteLocation.class, id));
        } finally {
            em.close();
        }
    }

    public List<UniteLocation> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM UniteLocation u JOIN FETCH u.immeuble ORDER BY u.immeuble.nom, u.numeroUnite",
                    UniteLocation.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<UniteLocation> findByImmeuble(Long immeubleId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<UniteLocation> q = em.createQuery(
                "SELECT u FROM UniteLocation u WHERE u.immeuble.id = :iid ORDER BY u.numeroUnite",
                UniteLocation.class);
            q.setParameter("iid", immeubleId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<UniteLocation> findDisponibles() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT u FROM UniteLocation u JOIN FETCH u.immeuble WHERE u.statut = 'DISPONIBLE' ORDER BY u.loyerMensuel",
                UniteLocation.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<UniteLocation> findDisponiblesWithFilters(Integer pieces, BigDecimal loyerMax) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                "SELECT u FROM UniteLocation u JOIN FETCH u.immeuble WHERE u.statut = 'DISPONIBLE'");
            if (pieces != null) jpql.append(" AND u.nombrePieces = :pieces");
            if (loyerMax != null) jpql.append(" AND u.loyerMensuel <= :loyerMax");
            jpql.append(" ORDER BY u.loyerMensuel");

            TypedQuery<UniteLocation> q = em.createQuery(jpql.toString(), UniteLocation.class);
            if (pieces != null) q.setParameter("pieces", pieces);
            if (loyerMax != null) q.setParameter("loyerMax", loyerMax);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            UniteLocation u = em.find(UniteLocation.class, id);
            if (u != null) em.remove(u);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erreur suppression unité", e);
        } finally {
            em.close();
        }
    }

    public long countByStatut(String statut) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM UniteLocation u WHERE u.statut = :s", Long.class)
                     .setParameter("s", UniteLocation.Statut.valueOf(statut))
                     .getSingleResult();
        } finally {
            em.close();
        }
    }
}
