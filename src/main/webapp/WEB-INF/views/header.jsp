<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion Locations - ${pageTitle}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<nav class="navbar">
    <div class="navbar-brand">
        <i class="fas fa-building"></i> GestionLoc
    </div>
    <ul class="navbar-menu">
        <li><a href="${pageContext.request.contextPath}/dashboard"><i class="fas fa-tachometer-alt"></i> Tableau de bord</a></li>
        <li><a href="${pageContext.request.contextPath}/immeubles/"><i class="fas fa-building"></i> Immeubles</a></li>
        <li><a href="${pageContext.request.contextPath}/unites/"><i class="fas fa-door-open"></i> Unités</a></li>
        <li><a href="${pageContext.request.contextPath}/locataires/"><i class="fas fa-users"></i> Locataires</a></li>
        <li><a href="${pageContext.request.contextPath}/contrats/"><i class="fas fa-file-contract"></i> Contrats</a></li>
        <li><a href="${pageContext.request.contextPath}/paiements/"><i class="fas fa-money-bill"></i> Paiements</a></li>
        <c:if test="${session.role == 'ADMIN'}">
        <li><a href="${pageContext.request.contextPath}/admin/"><i class="fas fa-cog"></i> Admin</a></li>
        </c:if>
    </ul>
    <div class="navbar-user">
        <span><i class="fas fa-user"></i> ${sessionScope.utilisateur.username}</span>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm btn-danger">
            <i class="fas fa-sign-out-alt"></i> Déconnexion
        </a>
    </div>
</nav>
<div class="container">
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success"><i class="fas fa-check-circle"></i> ${sessionScope.message}</div>
        <c:remove var="message" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.erreur}">
        <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${sessionScope.erreur}</div>
        <c:remove var="erreur" scope="session"/>
    </c:if>
    <c:if test="${not empty requestScope.erreur}">
        <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${requestScope.erreur}</div>
    </c:if>
