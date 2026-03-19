<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${empty immeuble ? 'Nouvel immeuble' : 'Modifier immeuble'}" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-building"></i>
        <c:choose>
            <c:when test="${empty immeuble}">Nouvel Immeuble</c:when>
            <c:otherwise>Modifier : ${immeuble.nom}</c:otherwise>
        </c:choose>
    </h2>
    <a href="${pageContext.request.contextPath}/immeubles/" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Retour
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/immeubles/" method="post" class="form-grid">
            <input type="hidden" name="action" value="${empty immeuble ? 'creer' : 'modifier'}">
            <c:if test="${not empty immeuble}">
                <input type="hidden" name="id" value="${immeuble.id}">
            </c:if>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Nom de l'immeuble *</label>
                    <input type="text" name="nom" class="form-control" value="${immeuble.nom}" required>
                </div>
                <div class="form-group col-md-6">
                    <label>Propriétaire</label>
                    <input type="text" name="proprietaire" class="form-control" value="${immeuble.proprietaire}">
                </div>
            </div>

            <div class="form-group">
                <label>Adresse *</label>
                <input type="text" name="adresse" class="form-control" value="${immeuble.adresse}" required>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Ville</label>
                    <input type="text" name="ville" class="form-control" value="${immeuble.ville}">
                </div>
                <div class="form-group col-md-3">
                    <label>Code postal</label>
                    <input type="text" name="codePostal" class="form-control" value="${immeuble.codePostal}">
                </div>
                <div class="form-group col-md-3">
                    <label>Année construction</label>
                    <input type="number" name="anneeConstruction" class="form-control"
                           value="${immeuble.anneeConstruction}" min="1800" max="2100">
                </div>
            </div>

            <div class="form-group">
                <label>Nombre d'unités</label>
                <input type="number" name="nombreUnites" class="form-control"
                       value="${immeuble.nombreUnites}" min="0">
            </div>

            <div class="form-group">
                <label>Équipements</label>
                <input type="text" name="equipements" class="form-control" value="${immeuble.equipements}"
                       placeholder="Ascenseur, parking, gardien...">
            </div>

            <div class="form-group">
                <label>Description</label>
                <textarea name="description" class="form-control" rows="3">${immeuble.description}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i>
                    <c:choose>
                        <c:when test="${empty immeuble}">Créer l'immeuble</c:when>
                        <c:otherwise>Enregistrer les modifications</c:otherwise>
                    </c:choose>
                </button>
                <a href="${pageContext.request.contextPath}/immeubles/" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
