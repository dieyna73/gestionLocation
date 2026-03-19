<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Unités de location" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-door-open"></i> Unités de Location</h2>
    <div>
        <a href="${pageContext.request.contextPath}/unites/disponibles" class="btn btn-success">
            <i class="fas fa-check-circle"></i> Disponibles
        </a>
        <a href="${pageContext.request.contextPath}/unites/nouveau" class="btn btn-primary">
            <i class="fas fa-plus"></i> Nouvelle unité
        </a>
    </div>
</div>

<div class="card">
    <div class="card-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Numéro</th><th>Immeuble</th><th>Pièces</th>
                <th>Superficie</th><th>Loyer mensuel</th><th>Statut</th><th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${unites}">
                <tr>
                    <td><strong>${u.numeroUnite}</strong></td>
                    <td>${u.immeuble.nom}</td>
                    <td>${u.nombrePieces} pièce(s)</td>
                    <td>${u.superficie} m²</td>
                    <td><fmt:formatNumber value="${u.loyerMensuel}" type="currency" currencySymbol="€"/></td>
                    <td>
                        <span class="badge badge-${u.statut == 'DISPONIBLE' ? 'success' : u.statut == 'OCCUPEE' ? 'danger' : 'warning'}">
                            <c:choose>
                                <c:when test="${u.statut == 'DISPONIBLE'}"><i class="fas fa-check"></i> Disponible</c:when>
                                <c:when test="${u.statut == 'OCCUPEE'}"><i class="fas fa-times"></i> Occupée</c:when>
                                <c:otherwise><i class="fas fa-tools"></i> En travaux</c:otherwise>
                            </c:choose>
                        </span>
                    </td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/unites/modifier/${u.id}"
                           class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                        <a href="${pageContext.request.contextPath}/unites/supprimer/${u.id}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Supprimer cette unité ?')">
                            <i class="fas fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty unites}">
                <tr><td colspan="7" class="text-center text-muted">Aucune unité trouvée</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
