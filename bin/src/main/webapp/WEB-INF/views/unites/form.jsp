<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${empty unite ? 'Nouvelle unité' : 'Modifier unité'}" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-door-open"></i>
        <c:choose><c:when test="${empty unite}">Nouvelle Unité</c:when>
        <c:otherwise>Modifier : ${unite.numeroUnite}</c:otherwise></c:choose>
    </h2>
    <a href="${pageContext.request.contextPath}/unites/" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Retour
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/unites/" method="post" class="form-grid">
            <input type="hidden" name="action" value="${empty unite ? 'creer' : 'modifier'}">
            <c:if test="${not empty unite}">
                <input type="hidden" name="id" value="${unite.id}">
            </c:if>

            <div class="form-group">
                <label>Immeuble *</label>
                <select name="immeubleId" class="form-control" required>
                    <option value="">-- Sélectionner un immeuble --</option>
                    <c:forEach var="i" items="${immeubles}">
                        <option value="${i.id}" ${unite.immeuble.id == i.id ? 'selected' : ''}>${i.nom} - ${i.ville}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-row">
                <div class="form-group col-md-4">
                    <label>Numéro d'unité *</label>
                    <input type="text" name="numeroUnite" class="form-control" value="${unite.numeroUnite}" required>
                </div>
                <div class="form-group col-md-4">
                    <label>Nombre de pièces</label>
                    <input type="number" name="nombrePieces" class="form-control" value="${unite.nombrePieces}" min="1">
                </div>
                <div class="form-group col-md-4">
                    <label>Superficie (m²)</label>
                    <input type="number" name="superficie" class="form-control" value="${unite.superficie}" step="0.01">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Loyer mensuel (€) *</label>
                    <input type="number" name="loyerMensuel" class="form-control" value="${unite.loyerMensuel}"
                           step="0.01" min="0" required>
                </div>
                <div class="form-group col-md-6">
                    <label>Charges mensuelles (€)</label>
                    <input type="number" name="chargesMensuelles" class="form-control"
                           value="${unite.chargesMensuelles}" step="0.01" min="0">
                </div>
            </div>

            <c:if test="${not empty unite}">
            <div class="form-group">
                <label>Statut</label>
                <select name="statut" class="form-control">
                    <option value="DISPONIBLE" ${unite.statut == 'DISPONIBLE' ? 'selected' : ''}>Disponible</option>
                    <option value="OCCUPEE" ${unite.statut == 'OCCUPEE' ? 'selected' : ''}>Occupée</option>
                    <option value="EN_TRAVAUX" ${unite.statut == 'EN_TRAVAUX' ? 'selected' : ''}>En travaux</option>
                </select>
            </div>
            </c:if>

            <div class="form-group">
                <label>Équipements</label>
                <input type="text" name="equipements" class="form-control" value="${unite.equipements}"
                       placeholder="Cuisine équipée, balcon, cave...">
            </div>

            <div class="form-group">
                <label>Description</label>
                <textarea name="description" class="form-control" rows="3">${unite.description}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i>
                    <c:choose><c:when test="${empty unite}">Créer l'unité</c:when>
                    <c:otherwise>Enregistrer</c:otherwise></c:choose>
                </button>
                <a href="${pageContext.request.contextPath}/unites/" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
