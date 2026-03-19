<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Détail contrat" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-file-contract"></i> Contrat ${contrat.numeroContrat}</h2>
    <div>
        <a href="${pageContext.request.contextPath}/paiements/contrat/${contrat.id}"
           class="btn btn-success"><i class="fas fa-money-bill"></i> Paiements</a>
        <a href="${pageContext.request.contextPath}/paiements/nouveau?contratId=${contrat.id}"
           class="btn btn-primary"><i class="fas fa-plus"></i> Paiement</a>
        <a href="${pageContext.request.contextPath}/contrats/" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Retour
        </a>
    </div>
</div>

<div class="detail-grid">
    <div class="card">
        <div class="card-header"><h3><i class="fas fa-info-circle"></i> Détails du contrat</h3></div>
        <div class="card-body">
            <table class="table table-detail">
                <tr><th>N° Contrat</th><td><strong>${contrat.numeroContrat}</strong></td></tr>
                <tr><th>Statut</th>
                    <td><span class="badge badge-${contrat.statut == 'ACTIF' ? 'success' : 'secondary'}">${contrat.statut}</span></td>
                </tr>
                <tr><th>Date début</th><td>${contrat.dateDebut}</td></tr>
                <tr><th>Date fin</th><td>${contrat.dateFin != null ? contrat.dateFin : 'Indéterminée'}</td></tr>
                <tr><th>Loyer mensuel</th>
                    <td><strong><fmt:formatNumber value="${contrat.loyerMensuel}" type="currency" currencySymbol="€"/></strong></td>
                </tr>
                <tr><th>Dépôt de garantie</th>
                    <td><fmt:formatNumber value="${contrat.depotGarantie}" type="currency" currencySymbol="€"/></td>
                </tr>
                <tr><th>Créé le</th><td>${contrat.dateCreation}</td></tr>
            </table>
        </div>
    </div>

    <div class="card">
        <div class="card-header"><h3><i class="fas fa-user"></i> Locataire</h3></div>
        <div class="card-body">
            <table class="table table-detail">
                <tr><th>Nom</th><td>${contrat.locataire.prenom} ${contrat.locataire.nom}</td></tr>
                <tr><th>Email</th><td>${contrat.locataire.email}</td></tr>
                <tr><th>Téléphone</th><td>${contrat.locataire.telephone}</td></tr>
            </table>
            <a href="${pageContext.request.contextPath}/locataires/profil/${contrat.locataire.id}"
               class="btn btn-sm btn-info"><i class="fas fa-eye"></i> Voir profil</a>
        </div>
    </div>

    <div class="card">
        <div class="card-header"><h3><i class="fas fa-door-open"></i> Unité louée</h3></div>
        <div class="card-body">
            <table class="table table-detail">
                <tr><th>Immeuble</th><td>${contrat.uniteLocation.immeuble.nom}</td></tr>
                <tr><th>Unité</th><td>${contrat.uniteLocation.numeroUnite}</td></tr>
                <tr><th>Adresse</th><td>${contrat.uniteLocation.immeuble.adresse}</td></tr>
                <tr><th>Superficie</th><td>${contrat.uniteLocation.superficie} m²</td></tr>
                <tr><th>Pièces</th><td>${contrat.uniteLocation.nombrePieces}</td></tr>
            </table>
        </div>
    </div>

    <c:if test="${not empty contrat.conditions}">
    <div class="card">
        <div class="card-header"><h3><i class="fas fa-clipboard-list"></i> Conditions particulières</h3></div>
        <div class="card-body"><p>${contrat.conditions}</p></div>
    </div>
    </c:if>
</div>

<jsp:include page="../footer.jsp"/>
