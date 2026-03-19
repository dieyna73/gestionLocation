<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Inscription locataire" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-user-plus"></i> Inscription Locataire</h2>
    <a href="${pageContext.request.contextPath}/locataires/" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Retour
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/locataires/" method="post">
            <input type="hidden" name="action" value="inscrire">

            <h4 class="section-title"><i class="fas fa-user"></i> Informations personnelles</h4>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Nom *</label>
                    <input type="text" name="nom" class="form-control" required>
                </div>
                <div class="form-group col-md-6">
                    <label>Prénom *</label>
                    <input type="text" name="prenom" class="form-control" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Email *</label>
                    <input type="email" name="email" class="form-control" required>
                </div>
                <div class="form-group col-md-6">
                    <label>Téléphone *</label>
                    <input type="tel" name="telephone" class="form-control" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-4">
                    <label>Date de naissance</label>
                    <input type="date" name="dateNaissance" class="form-control">
                </div>
                <div class="form-group col-md-4">
                    <label>Pièce d'identité</label>
                    <select name="pieceIdentite" class="form-control">
                        <option value="">-- Sélectionner --</option>
                        <option value="CIN">Carte d'identité</option>
                        <option value="PASSEPORT">Passeport</option>
                        <option value="PERMIS">Permis de conduire</option>
                    </select>
                </div>
                <div class="form-group col-md-4">
                    <label>Numéro pièce</label>
                    <input type="text" name="numeroPiece" class="form-control">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-8">
                    <label>Adresse</label>
                    <input type="text" name="adresse" class="form-control">
                </div>
                <div class="form-group col-md-4">
                    <label>Profession</label>
                    <input type="text" name="profession" class="form-control">
                </div>
            </div>

            <h4 class="section-title mt-4"><i class="fas fa-lock"></i> Compte d'accès</h4>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Nom d'utilisateur *</label>
                    <input type="text" name="username" class="form-control" required>
                </div>
                <div class="form-group col-md-6">
                    <label>Mot de passe *</label>
                    <input type="password" name="motDePasse" class="form-control" required minlength="6">
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-user-plus"></i> Inscrire le locataire
                </button>
                <a href="${pageContext.request.contextPath}/locataires/" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
