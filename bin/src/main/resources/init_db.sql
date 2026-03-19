-- ============================================================
-- Script d'initialisation - Gestion des Locations d'Immeubles
-- ============================================================

CREATE DATABASE IF NOT EXISTS gestion_locations
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestion_locations;

-- Les tables sont créées automatiquement par Hibernate (hbm2ddl.auto=update)
-- Ce script insère des données de test

-- Utilisateur admin par défaut
-- username: admin | password: Admin@123
INSERT INTO utilisateurs (username, mot_de_passe, email, role, actif, date_creation)
VALUES (
    'admin',
    '$2a$12$LcpNoHBxrBcxOXGkMJX6XedKsmEjT1HBEbN.N6dYBZk7h5VKZ7B.q',
    'admin@gestionloc.com',
    'ADMIN',
    1,
    NOW()
) ON DUPLICATE KEY UPDATE username = username;

-- Immeubles de démonstration
INSERT INTO immeubles (nom, adresse, ville, code_postal, nombre_unites, description, equipements, annee_construction, proprietaire)
VALUES
    ('Résidence Les Palmiers', '12 Avenue des Palmiers', 'Dakar', '10000', 8, 'Belle résidence moderne', 'Ascenseur, Parking, Gardien', 2015, 'SARL Immobilière Dakar'),
    ('Immeuble Centre-Ville', '5 Rue de la République', 'Dakar', '10001', 12, 'Immeuble en plein centre', 'Parking souterrain, Interphone', 2010, 'M. Diallo'),
    ('Villa Mer Bleue', '88 Boulevard de la Corniche', 'Dakar', '10002', 4, 'Vue sur mer exceptionnelle', 'Vue mer, Terrasse, Parking', 2018, 'Mme Ndoye')
ON DUPLICATE KEY UPDATE nom = nom;
