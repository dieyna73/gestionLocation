<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Enregistrer paiement" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-money-bill-wave"></i> Enregistrer un Paiement</h2>
    <a href="${pageContext.request.contextPath}/paiements/" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Retour
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/paiements/" method="post">
            <input type="hidden" name="action" value="payer">

            <div class="form-group">
                <label>Contrat *</label>
                <select name="contratId" class="form-control" required>
                    <option value="">-- Sélectionner un contrat actif --</option>
                    <c:forEach var="c" items="${contrats}">
                        <option value="${c.id}" ${param.contratId == c.id ? 'selected' : ''}>
                            ${c.numeroContrat} - ${c.locataire.prenom} ${c.locataire.nom}
                            (${c.uniteLocation.immeuble.nom} - ${c.uniteLocation.numeroUnite})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-row">
                <div class="form-group col-md-4">
                    <label>Montant (€) *</label>
                    <input type="number" name="montant" class="form-control" step="0.01" min="0" required>
                </div>
                <div class="form-group col-md-4">
                    <label>Date d'échéance *</label>
                    <input type="date" name="dateEcheance" class="form-control" required>
                </div>
                <div class="form-group col-md-4">
                    <label>Période (mois)</label>
                    <input type="text" name="periodeMois" class="form-control" placeholder="Ex: Janvier 2025">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Mode de paiement</label>
                    <select name="modePaiement" class="form-control">
                        <option value="">-- Sélectionner --</option>
                        <option value="ESPECES">Espèces</option>
                        <option value="VIREMENT">Virement bancaire</option>
                        <option value="CHEQUE">Chèque</option>
                        <option value="CARTE">Carte bancaire</option>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label>Commentaire</label>
                    <input type="text" name="commentaire" class="form-control" placeholder="Observation...">
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-success">
                    <i class="fas fa-check-circle"></i> Enregistrer le paiement
                </button>
                <a href="${pageContext.request.contextPath}/paiements/" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
