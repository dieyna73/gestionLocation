# Gestion des Locations d'Immeubles - JEE

Application web JEE complète pour la gestion des locations d'immeubles.

## Technologies
- **Java 11** + Jakarta EE (Servlets, JSP, JSTL)
- **Hibernate / JPA** pour la persistance
- **MySQL 8** (ou PostgreSQL)
- **Apache Tomcat 10**
- **Maven** pour la gestion des dépendances
- **BCrypt** pour le hachage des mots de passe

---

## Structure du projet

```
gestion-locations/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/locations/
    │   │   ├── entities/         ← Entités JPA
    │   │   │   ├── Immeuble.java
    │   │   │   ├── UniteLocation.java
    │   │   │   ├── Locataire.java
    │   │   │   ├── ContratLocation.java
    │   │   │   ├── Paiement.java
    │   │   │   └── Utilisateur.java
    │   │   ├── dao/              ← Couche d'accès aux données
    │   │   ├── services/         ← Logique métier
    │   │   ├── servlets/         ← Contrôleurs HTTP
    │   │   └── utils/            ← JPAUtil, PasswordUtil
    │   ├── resources/
    │   │   ├── META-INF/persistence.xml
    │   │   └── init_db.sql
    │   └── webapp/
    │       ├── WEB-INF/
    │       │   ├── web.xml
    │       │   └── views/        ← Pages JSP
    │       ├── css/style.css
    │       └── js/app.js
    └── test/
```

---

## Installation

### 1. Prérequis
- JDK 11+
- Apache Tomcat 10.x
- MySQL 8.x
- Maven 3.8+
- IntelliJ IDEA

### 2. Base de données MySQL

```sql
CREATE DATABASE gestion_locations CHARACTER SET utf8mb4;
CREATE USER 'locations_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON gestion_locations.* TO 'locations_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configuration JPA

Modifier `src/main/resources/META-INF/persistence.xml` :
```xml
<property name="jakarta.persistence.jdbc.url"
          value="jdbc:mysql://localhost:3306/gestion_locations?..."/>
<property name="jakarta.persistence.jdbc.user" value="votre_user"/>
<property name="jakarta.persistence.jdbc.password" value="votre_mdp"/>
```

### 4. Ouvrir dans IntelliJ

1. `File > Open` → sélectionner le dossier `gestion-locations`
2. IntelliJ détecte automatiquement Maven → cliquer **Import**
3. `File > Project Structure > SDKs` → choisir JDK 11+
4. Configurer Tomcat : `Run > Edit Configurations > + > Tomcat Server > Local`
   - **Application server** : chemin vers Tomcat 10
   - **Deployment** : `+ > Artifact > gestion-locations:war exploded`
   - **Application context** : `/gestion-locations`
5. Cliquer **Run** ▶

### 5. Accès à l'application

```
http://localhost:8080/gestion-locations/
```

**Compte admin par défaut :**
- Username : `admin`
- Password : `Admin@123`

---

## Fonctionnalités

| Module | Fonctionnalités |
|--------|----------------|
| **Immeubles** | CRUD complet, recherche, détail avec unités |
| **Unités** | CRUD, filtres disponibilité/loyer/pièces |
| **Locataires** | Inscription avec compte, profil, historique |
| **Contrats** | Création, suivi statut, résiliation |
| **Paiements** | Enregistrement, historique, retards |
| **Dashboard** | Statistiques, alertes retards, raccourcis |
| **Sécurité** | Authentification, sessions, filtre URL |

---

## Architecture MVC

```
HTTP Request
     │
     ▼
AuthFilter (sécurité)
     │
     ▼
Servlet (contrôleur)  ←→  Service (métier)  ←→  DAO (données)  ←→  MySQL
     │                                                              (Hibernate/JPA)
     ▼
JSP (vue)
```
