<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Unités disponibles" scope="request"/>
<jsp:include page="../header.jsp"/>

<div class="page-header">
    <h2><i class="fas fa-check-circle text-success"></i> Unités Disponibles</h2>
</div>

<div class="card">
    <div class="card-header"><h3><i class="fas fa-filter"></i> Filtres</h3></div>
    <div class="card-body">
        <form method="get" class="filter-form">
            <div class="form-row">
                <div class="form-group col-md-4">
                    <label>Nombre de pièces</label>
                    <select name="pieces" class="form-control">
                        <option value="">Tous</option>
                        <c:forEach var="i" begin="1" end="6">
                            <option value="${i}" ${pieces == i ? 'selected' : ''}>${i} pièce(s)</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-4">
                    <label>Loyer maximum (€)</label>
                    <input type="number" name="loyerMax" class="form-control" value="${loyerMax}" step="50" min="0">
                </div>
                <div class="form-group col-md-4 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary mr-2">
                        <i class="fas fa-search"></i> Filtrer
                    </button>
                    <a href="${pageContext.request.contextPath}/unites/disponibles" class="btn btn-secondary">
                        <i class="fas fa-times"></i> Réinitialiser
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="card mt-3">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty unites}">
                <p class="text-muted text-center py-4">
                    <i class="fas fa-search fa-2x"></i><br>Aucune unité disponible avec ces critères
                </p>
            </c:when>
            <c:otherwise>
                <div class="unite-grid">
                    <c:forEach var="u" items="${unites}">
                        <div class="unite-card">
                            <div class="unite-card-header">
                                <span class="unite-numero">${u.immeuble.nom} - Unité ${u.numeroUnite}</span>
                                <span class="badge badge-success">Disponible</span>
                            </div>
                            <div class="unite-card-body">
                                <div class="unite-info">
                                    <span><i class="fas fa-door-open"></i> ${u.nombrePieces} pièce(s)</span>
                                    <span><i class="fas fa-ruler-combined"></i> ${u.superficie} m²</span>
                                    <span><i class="fas fa-map-marker-alt"></i> ${u.immeuble.ville}</span>
                                </div>
                                <div class="unite-price">
                                    <fmt:formatNumber value="${u.loyerMensuel}" type="currency" currencySymbol="€"/>
                                    <small>/mois</small>
                                </div>
                                <c:if test="${not empty u.equipements}">
                                    <p class="unite-equipements"><i class="fas fa-star"></i> ${u.equipements}</p>
                                </c:if>
                            </div>
                            <div class="unite-card-footer">
                                <a href="${pageContext.request.contextPath}/contrats/nouveau?uniteId=${u.id}"
                                   class="btn btn-primary btn-block">
                                    <i class="fas fa-file-signature"></i> Créer un contrat
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
