<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Immeubles" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-building"></i> Gestion des Immeubles</h2>
    <a href="${pageContext.request.contextPath}/immeubles/nouveau" class="btn btn-primary">
        <i class="fas fa-plus"></i> Nouvel immeuble
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form method="get" class="search-form">
            <div class="input-group">
                <input type="text" name="search" value="${search}" class="form-control"
                       placeholder="Rechercher un immeuble...">
                <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i></button>
                <a href="${pageContext.request.contextPath}/immeubles/" class="btn btn-secondary">
                    <i class="fas fa-times"></i>
                </a>
            </div>
        </form>
    </div>
</div>

<div class="card mt-3">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty immeubles}">
                <p class="text-muted text-center py-4">
                    <i class="fas fa-inbox fa-2x"></i><br>Aucun immeuble trouvé
                </p>
            </c:when>
            <c:otherwise>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Nom</th><th>Adresse</th><th>Ville</th>
                        <th>Unités</th><th>Propriétaire</th><th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="immeuble" items="${immeubles}">
                        <tr>
                            <td><strong>${immeuble.nom}</strong></td>
                            <td>${immeuble.adresse}</td>
                            <td>${immeuble.ville}</td>
                            <td><span class="badge badge-info">${immeuble.nombreUnites}</span></td>
                            <td>${immeuble.proprietaire}</td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/immeubles/detail/${immeuble.id}"
                                   class="btn btn-sm btn-info" title="Détail">
                                    <i class="fas fa-eye"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/immeubles/modifier/${immeuble.id}"
                                   class="btn btn-sm btn-warning" title="Modifier">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/immeubles/supprimer/${immeuble.id}"
                                   class="btn btn-sm btn-danger" title="Supprimer"
                                   onclick="return confirm('Supprimer cet immeuble ?')">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
