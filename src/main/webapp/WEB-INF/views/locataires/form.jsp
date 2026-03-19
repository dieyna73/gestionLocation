<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Modifier locataire" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-user-edit"></i> Modifier : ${locataire.prenom} ${locataire.nom}</h2>
    <a href="${pageContext.request.contextPath}/locataires/" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Retour
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/locataires/" method="post">
            <input type="hidden" name="action" value="modifier">
            <input type="hidden" name="id" value="${locataire.id}">

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Nom *</label>
                    <input type="text" name="nom" class="form-control" value="${locataire.nom}" required>
                </div>
                <div class="form-group col-md-6">
                    <label>Prénom *</label>
                    <input type="text" name="prenom" class="form-control" value="${locataire.prenom}" required>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Email *</label>
                    <input type="email" name="email" class="form-control" value="${locataire.email}" required>
                </div>
                <div class="form-group col-md-6">
                    <label>Téléphone</label>
                    <input type="tel" name="telephone" class="form-control" value="${locataire.telephone}">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-4">
                    <label>Date de naissance</label>
                    <input type="date" name="dateNaissance" class="form-control" value="${locataire.dateNaissance}">
                </div>
                <div class="form-group col-md-4">
                    <label>Pièce d'identité</label>
                    <select name="pieceIdentite" class="form-control">
                        <option value="">--</option>
                        <option value="CIN" ${locataire.pieceIdentite == 'CIN' ? 'selected' : ''}>CIN</option>
                        <option value="PASSEPORT" ${locataire.pieceIdentite == 'PASSEPORT' ? 'selected' : ''}>Passeport</option>
                        <option value="PERMIS" ${locataire.pieceIdentite == 'PERMIS' ? 'selected' : ''}>Permis</option>
                    </select>
                </div>
                <div class="form-group col-md-4">
                    <label>Numéro pièce</label>
                    <input type="text" name="numeroPiece" class="form-control" value="${locataire.numeroPiece}">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-8">
                    <label>Adresse</label>
                    <input type="text" name="adresse" class="form-control" value="${locataire.adresse}">
                </div>
                <div class="form-group col-md-4">
                    <label>Profession</label>
                    <input type="text" name="profession" class="form-control" value="${locataire.profession}">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Enregistrer</button>
                <a href="${pageContext.request.contextPath}/locataires/" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
