<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Paiements" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-money-bill-wave"></i> ${not empty titre ? titre : 'Paiements'}
        <c:if test="${not empty contrat}"> - ${contrat.numeroContrat}</c:if>
    </h2>
    <a href="${pageContext.request.contextPath}/paiements/nouveau" class="btn btn-primary">
        <i class="fas fa-plus"></i> Enregistrer un paiement
    </a>
</div>

<div class="card">
    <div class="card-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>N° Reçu</th><th>Contrat</th><th>Période</th><th>Montant</th>
                <th>Échéance</th><th>Date paiement</th><th>Mode</th><th>Statut</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${paiements}">
                <tr class="${p.statut == 'EN_RETARD' ? 'row-danger' : p.statut == 'PAYE' ? 'row-success' : ''}">
                    <td>${not empty p.numeroRecu ? p.numeroRecu : '—'}</td>
                    <td><a href="${pageContext.request.contextPath}/contrats/detail/${p.contrat.id}">
                        ${p.contrat.numeroContrat}</a></td>
                    <td>${p.periodeMois}</td>
                    <td><strong><fmt:formatNumber value="${p.montant}" type="currency" currencySymbol="€"/></strong></td>
                    <td>${p.dateEcheance}</td>
                    <td>${not empty p.datePaiement ? p.datePaiement : '—'}</td>
                    <td>${not empty p.modePaiement ? p.modePaiement : '—'}</td>
                    <td>
                        <span class="badge badge-${p.statut == 'PAYE' ? 'success' : p.statut == 'EN_RETARD' ? 'danger' : 'warning'}">
                            ${p.statut}
                        </span>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty paiements}">
                <tr><td colspan="8" class="text-center text-muted">Aucun paiement trouvé</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
