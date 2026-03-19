<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Locataires" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-users"></i> Gestion des Locataires</h2>
    <a href="${pageContext.request.contextPath}/locataires/inscription" class="btn btn-primary">
        <i class="fas fa-user-plus"></i> Nouveau locataire
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form method="get" class="search-form">
            <div class="input-group">
                <input type="text" name="search" value="${search}" class="form-control"
                       placeholder="Rechercher par nom, prénom ou email...">
                <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i></button>
                <a href="${pageContext.request.contextPath}/locataires/" class="btn btn-secondary">
                    <i class="fas fa-times"></i>
                </a>
            </div>
        </form>
    </div>
</div>

<div class="card mt-3">
    <div class="card-body">
        <table class="table table-hover">
            <thead>
            <tr><th>Nom complet</th><th>Email</th><th>Téléphone</th><th>Profession</th><th>Inscrit le</th><th>Actions</th></tr>
            </thead>
            <tbody>
            <c:forEach var="l" items="${locataires}">
                <tr>
                    <td><strong>${l.prenom} ${l.nom}</strong></td>
                    <td>${l.email}</td>
                    <td>${l.telephone}</td>
                    <td>${l.profession}</td>
                    <td>${l.dateInscription}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/locataires/profil/${l.id}"
                           class="btn btn-sm btn-info"><i class="fas fa-eye"></i></a>
                        <a href="${pageContext.request.contextPath}/locataires/modifier/${l.id}"
                           class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                        <a href="${pageContext.request.contextPath}/locataires/supprimer/${l.id}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Supprimer ce locataire ?')">
                            <i class="fas fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty locataires}">
                <tr><td colspan="6" class="text-center text-muted">Aucun locataire trouvé</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
