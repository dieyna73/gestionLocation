<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - GestionLoc</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="login-page">
<div class="login-container">
    <div class="login-card">
        <div class="login-logo">
            <i class="fas fa-building fa-3x"></i>
            <h1>GestionLoc</h1>
            <p>Gestion des Locations d'Immeubles</p>
        </div>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${erreur}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
            <div class="form-group">
                <label for="username"><i class="fas fa-user"></i> Nom d'utilisateur</label>
                <input type="text" id="username" name="username" class="form-control"
                       placeholder="Entrez votre nom d'utilisateur" required autofocus>
            </div>
            <div class="form-group">
                <label for="motDePasse"><i class="fas fa-lock"></i> Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse" class="form-control"
                       placeholder="Entrez votre mot de passe" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">
                <i class="fas fa-sign-in-alt"></i> Se connecter
            </button>
        </form>
        <div class="login-footer">
            <a href="${pageContext.request.contextPath}/locataires/inscription">
                <i class="fas fa-user-plus"></i> S'inscrire comme locataire
            </a>
        </div>
    </div>
</div>
</body>
</html>
