<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Nouveau contrat" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-file-signature"></i> Nouveau Contrat de Location</h2>
    <a href="${pageContext.request.contextPath}/contrats/" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Retour
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/contrats/" method="post">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Unité disponible *</label>
                    <select name="uniteId" class="form-control" required>
                        <option value="">-- Sélectionner une unité --</option>
                        <c:forEach var="u" items="${unites}">
                            <option value="${u.id}" ${param.uniteId == u.id ? 'selected' : ''}>
                                ${u.immeuble.nom} - Unité ${u.numeroUnite} (${u.nombrePieces}p, ${u.loyerMensuel}€/mois)
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label>Locataire *</label>
                    <select name="locataireId" class="form-control" required>
                        <option value="">-- Sélectionner un locataire --</option>
                        <c:forEach var="l" items="${locataires}">
                            <option value="${l.id}" ${param.locataireId == l.id ? 'selected' : ''}>
                                ${l.prenom} ${l.nom} (${l.email})
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-4">
                    <label>Date de début *</label>
                    <input type="date" name="dateDebut" class="form-control" required>
                </div>
                <div class="form-group col-md-4">
                    <label>Date de fin</label>
                    <input type="date" name="dateFin" class="form-control">
                </div>
                <div class="form-group col-md-4">
                    <label>Loyer mensuel (€) *</label>
                    <input type="number" name="loyerMensuel" class="form-control" step="0.01" min="0" required>
                </div>
            </div>

            <div class="form-group">
                <label>Dépôt de garantie (€)</label>
                <input type="number" name="depotGarantie" class="form-control" step="0.01" min="0">
            </div>

            <div class="form-group">
                <label>Conditions particulières</label>
                <textarea name="conditions" class="form-control" rows="4"
                          placeholder="Clauses spéciales, interdictions, autorisations..."></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-file-signature"></i> Créer le contrat
                </button>
                <a href="${pageContext.request.contextPath}/contrats/" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
