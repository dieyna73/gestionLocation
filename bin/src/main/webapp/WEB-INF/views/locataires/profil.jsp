<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Profil locataire" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-user"></i> ${locataire.prenom} ${locataire.nom}</h2>
    <div>
        <a href="${pageContext.request.contextPath}/locataires/modifier/${locataire.id}"
           class="btn btn-warning"><i class="fas fa-edit"></i> Modifier</a>
        <a href="${pageContext.request.contextPath}/locataires/" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Retour
        </a>
    </div>
</div>

<div class="detail-grid">
    <div class="card">
        <div class="card-header"><h3><i class="fas fa-id-card"></i> Informations personnelles</h3></div>
        <div class="card-body">
            <table class="table table-detail">
                <tr><th>Nom complet</th><td>${locataire.prenom} ${locataire.nom}</td></tr>
                <tr><th>Email</th><td>${locataire.email}</td></tr>
                <tr><th>Téléphone</th><td>${locataire.telephone}</td></tr>
                <tr><th>Date de naissance</th><td>${locataire.dateNaissance}</td></tr>
                <tr><th>Pièce d'identité</th><td>${locataire.pieceIdentite} n°${locataire.numeroPiece}</td></tr>
                <tr><th>Adresse</th><td>${locataire.adresse}</td></tr>
                <tr><th>Profession</th><td>${locataire.profession}</td></tr>
                <tr><th>Inscrit le</th><td>${locataire.dateInscription}</td></tr>
            </table>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <h3><i class="fas fa-file-contract"></i> Contrats</h3>
            <a href="${pageContext.request.contextPath}/contrats/nouveau?locataireId=${locataire.id}"
               class="btn btn-sm btn-primary"><i class="fas fa-plus"></i> Nouveau</a>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty locataire.contrats}">
                    <p class="text-muted text-center">Aucun contrat</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead><tr><th>N° Contrat</th><th>Unité</th><th>Début</th><th>Statut</th></tr></thead>
                        <tbody>
                        <c:forEach var="c" items="${locataire.contrats}">
                            <tr>
                                <td><a href="${pageContext.request.contextPath}/contrats/detail/${c.id}">
                                    ${c.numeroContrat}</a></td>
                                <td>${c.uniteLocation.immeuble.nom} - ${c.uniteLocation.numeroUnite}</td>
                                <td>${c.dateDebut}</td>
                                <td>
                                    <span class="badge badge-${c.statut == 'ACTIF' ? 'success' : c.statut == 'EN_ATTENTE' ? 'warning' : 'secondary'}">
                                        ${c.statut}
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
