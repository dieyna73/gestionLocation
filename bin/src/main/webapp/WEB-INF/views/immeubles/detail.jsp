<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Détail immeuble" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-building"></i> ${immeuble.nom}</h2>
    <div>
        <a href="${pageContext.request.contextPath}/immeubles/modifier/${immeuble.id}" class="btn btn-warning">
            <i class="fas fa-edit"></i> Modifier
        </a>
        <a href="${pageContext.request.contextPath}/immeubles/" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Retour
        </a>
    </div>
</div>

<div class="detail-grid">
    <div class="card">
        <div class="card-header"><h3><i class="fas fa-info-circle"></i> Informations générales</h3></div>
        <div class="card-body">
            <table class="table table-detail">
                <tr><th>Nom</th><td>${immeuble.nom}</td></tr>
                <tr><th>Adresse</th><td>${immeuble.adresse}</td></tr>
                <tr><th>Ville</th><td>${immeuble.ville} ${immeuble.codePostal}</td></tr>
                <tr><th>Propriétaire</th><td>${immeuble.proprietaire}</td></tr>
                <tr><th>Nombre d'unités</th><td>${immeuble.nombreUnites}</td></tr>
                <tr><th>Année construction</th><td>${immeuble.anneeConstruction}</td></tr>
                <tr><th>Équipements</th><td>${immeuble.equipements}</td></tr>
                <tr><th>Description</th><td>${immeuble.description}</td></tr>
            </table>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <h3><i class="fas fa-door-open"></i> Unités de location</h3>
            <a href="${pageContext.request.contextPath}/unites/nouveau?immeubleId=${immeuble.id}"
               class="btn btn-sm btn-primary"><i class="fas fa-plus"></i> Ajouter</a>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty immeuble.unites}">
                    <p class="text-muted text-center">Aucune unité pour cet immeuble</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead><tr><th>N°</th><th>Pièces</th><th>Superficie</th><th>Loyer</th><th>Statut</th></tr></thead>
                        <tbody>
                        <c:forEach var="u" items="${immeuble.unites}">
                            <tr>
                                <td><strong>${u.numeroUnite}</strong></td>
                                <td>${u.nombrePieces} p.</td>
                                <td>${u.superficie} m²</td>
                                <td>${u.loyerMensuel} €</td>
                                <td>
                                    <span class="badge badge-${u.statut == 'DISPONIBLE' ? 'success' : u.statut == 'OCCUPEE' ? 'danger' : 'warning'}">
                                        ${u.statut}
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
