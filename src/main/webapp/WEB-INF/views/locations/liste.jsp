<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Contrats" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-file-contract"></i> Contrats de Location</h2>
    <a href="${pageContext.request.contextPath}/contrats/nouveau" class="btn btn-primary">
        <i class="fas fa-plus"></i> Nouveau contrat
    </a>
</div>

<div class="card">
    <div class="card-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>N° Contrat</th><th>Locataire</th><th>Unité / Immeuble</th>
                <th>Loyer</th><th>Début</th><th>Fin</th><th>Statut</th><th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="c" items="${contrats}">
                <tr>
                    <td><strong>${c.numeroContrat}</strong></td>
                    <td>${c.locataire.prenom} ${c.locataire.nom}</td>
                    <td>${c.uniteLocation.immeuble.nom} - ${c.uniteLocation.numeroUnite}</td>
                    <td><fmt:formatNumber value="${c.loyerMensuel}" type="currency" currencySymbol="€"/></td>
                    <td>${c.dateDebut}</td>
                    <td>${c.dateFin != null ? c.dateFin : '—'}</td>
                    <td>
                        <span class="badge badge-${c.statut == 'ACTIF' ? 'success' : c.statut == 'EN_ATTENTE' ? 'warning' : 'secondary'}">
                            ${c.statut}
                        </span>
                    </td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/contrats/detail/${c.id}"
                           class="btn btn-sm btn-info"><i class="fas fa-eye"></i></a>
                        <a href="${pageContext.request.contextPath}/paiements/contrat/${c.id}"
                           class="btn btn-sm btn-success" title="Paiements"><i class="fas fa-money-bill"></i></a>
                        <c:if test="${c.statut == 'ACTIF' || c.statut == 'EN_ATTENTE'}">
                            <a href="${pageContext.request.contextPath}/contrats/resilier/${c.id}"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Résilier ce contrat ?')">
                                <i class="fas fa-ban"></i>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty contrats}">
                <tr><td colspan="8" class="text-center text-muted">Aucun contrat</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
