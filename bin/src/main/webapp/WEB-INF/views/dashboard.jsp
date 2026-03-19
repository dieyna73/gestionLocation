<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Tableau de bord" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-tachometer-alt"></i> Tableau de bord</h2>
</div>

<!-- Statistiques -->
<div class="stats-grid">
    <div class="stat-card stat-blue">
        <div class="stat-icon"><i class="fas fa-building"></i></div>
        <div class="stat-info">
            <h3>${nbImmeubles}</h3>
            <p>Immeubles</p>
        </div>
    </div>
    <div class="stat-card stat-green">
        <div class="stat-icon"><i class="fas fa-door-open"></i></div>
        <div class="stat-info">
            <h3>${nbUnitesDisponibles}</h3>
            <p>Unités disponibles</p>
        </div>
    </div>
    <div class="stat-card stat-orange">
        <div class="stat-icon"><i class="fas fa-door-closed"></i></div>
        <div class="stat-info">
            <h3>${nbUnitesOccupees}</h3>
            <p>Unités occupées</p>
        </div>
    </div>
    <div class="stat-card stat-purple">
        <div class="stat-icon"><i class="fas fa-users"></i></div>
        <div class="stat-info">
            <h3>${nbLocataires}</h3>
            <p>Locataires</p>
        </div>
    </div>
    <div class="stat-card stat-teal">
        <div class="stat-icon"><i class="fas fa-file-contract"></i></div>
        <div class="stat-info">
            <h3>${nbContratsActifs}</h3>
            <p>Contrats actifs</p>
        </div>
    </div>
    <div class="stat-card stat-green">
        <div class="stat-icon"><i class="fas fa-euro-sign"></i></div>
        <div class="stat-info">
            <h3><fmt:formatNumber value="${totalEncaisse}" type="currency" currencySymbol="€"/></h3>
            <p>Total encaissé</p>
        </div>
    </div>
</div>

<div class="dashboard-grid">
    <!-- Contrats actifs -->
    <div class="card">
        <div class="card-header">
            <h3><i class="fas fa-file-contract"></i> Contrats actifs récents</h3>
            <a href="${pageContext.request.contextPath}/contrats/" class="btn btn-sm btn-primary">Voir tout</a>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty contratsActifs}">
                    <p class="text-muted text-center">Aucun contrat actif</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead><tr><th>N° Contrat</th><th>Locataire</th><th>Unité</th><th>Loyer</th></tr></thead>
                        <tbody>
                        <c:forEach var="c" items="${contratsActifs}" end="4">
                            <tr>
                                <td><strong>${c.numeroContrat}</strong></td>
                                <td>${c.locataire.prenom} ${c.locataire.nom}</td>
                                <td>${c.uniteLocation.immeuble.nom} - ${c.uniteLocation.numeroUnite}</td>
                                <td><fmt:formatNumber value="${c.loyerMensuel}" type="currency" currencySymbol="€"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Paiements en retard -->
    <div class="card">
        <div class="card-header">
            <h3><i class="fas fa-exclamation-triangle text-danger"></i> Paiements en retard</h3>
            <a href="${pageContext.request.contextPath}/paiements/en-retard" class="btn btn-sm btn-danger">Voir tout</a>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty paiementsEnRetard}">
                    <p class="text-success text-center"><i class="fas fa-check"></i> Aucun retard de paiement</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead><tr><th>Contrat</th><th>Période</th><th>Montant</th><th>Échéance</th></tr></thead>
                        <tbody>
                        <c:forEach var="p" items="${paiementsEnRetard}">
                            <tr class="row-danger">
                                <td>${p.contrat.numeroContrat}</td>
                                <td>${p.periodeMois}</td>
                                <td><fmt:formatNumber value="${p.montant}" type="currency" currencySymbol="€"/></td>
                                <td>${p.dateEcheance}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<!-- Raccourcis -->
<div class="card mt-4">
    <div class="card-header"><h3><i class="fas fa-bolt"></i> Actions rapides</h3></div>
    <div class="card-body">
        <div class="quick-actions">
            <a href="${pageContext.request.contextPath}/immeubles/nouveau" class="quick-btn">
                <i class="fas fa-plus-circle"></i><span>Nouvel immeuble</span>
            </a>
            <a href="${pageContext.request.contextPath}/unites/nouveau" class="quick-btn">
                <i class="fas fa-plus-circle"></i><span>Nouvelle unité</span>
            </a>
            <a href="${pageContext.request.contextPath}/locataires/inscription" class="quick-btn">
                <i class="fas fa-user-plus"></i><span>Nouveau locataire</span>
            </a>
            <a href="${pageContext.request.contextPath}/contrats/nouveau" class="quick-btn">
                <i class="fas fa-file-signature"></i><span>Nouveau contrat</span>
            </a>
            <a href="${pageContext.request.contextPath}/paiements/nouveau" class="quick-btn">
                <i class="fas fa-money-bill-wave"></i><span>Enregistrer paiement</span>
            </a>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
