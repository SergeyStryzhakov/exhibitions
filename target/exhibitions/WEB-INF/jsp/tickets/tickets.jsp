<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="myTag" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="tickets.manage.title"/></title>
    <link rel="stylesheet" href="${path}/assets/css/show.css">
</head>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<body style="background: rgba(186,196,179,0.6)">

<div class="container-xxl">
    <div class="table-responsive">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2><b><fmt:message key="tickets.manage.title"/></b></h2>
                    </div>
                </div>
                <div class="mt-2">
                    <label for="itemsPerPage"><fmt:message key="main.itemsPerPage"/></label>
                    <select class="form-select-sm" id="itemsPerPage">
                        <option value="5" ${sessionScope.ticketsPerPage == 5 ? 'selected' : ''}>5</option>
                        <option value="10" ${sessionScope.ticketsPerPage == 10 ? 'selected' : ''}>10</option>
                        <option value="15" ${sessionScope.ticketsPerPage == 15 ? 'selected' : ''}>15</option>
                    </select>
                </div>
            </div>
            <table class="table table-striped table-hover text-center">
                <thead>
                <tr>
                    <th>No</th>
                    <th><fmt:message key="tickets.manage.date"/></th>
                    <c:if test="${sessionScope.user.role == 'ROLE_ADMIN'}">
                        <th><fmt:message key="tickets.manage.user"/></th>
                    </c:if>
                    <th><fmt:message key="exhibition.halls"/></th>
                    <th><fmt:message key="tickets.manage.exhibition"/></th>
                    <th><fmt:message key="tickets.manage.price"/></th>
                    <th><fmt:message key="tickets.manage.operationdate"/></th>
                    <th><fmt:message key="tickets.manage.status"/></th>
                    <th><fmt:message key="manage.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="t" items="${tickets}" varStatus="status">
                    <tr>
                        <td>${status.count + ticketsPerPage * (page-1)}</td>
                        <td>${t.exhibition.startDate} - <br>${t.exhibition.finishDate}</td>
                        <c:if test="${sessionScope.user.role == 'ROLE_ADMIN'}">
                            <td><myTag:fullname user="${t.user}"/></td>
                        </c:if>
                        <td>${t.hall.name}</td>
                        <td class="text-center">
                            <a href="${path}/exhibitions/show?exid=${t.exhibition.id}">
                                <img class="img-thumbnail"
                                     src="${path}/assets/img/${t.exhibition.id}/${t.exhibition.image}"
                                     onError="this.onerror=null;this.src='${path}/assets/img/alt.jpg';"
                                     alt="${t.exhibition.title}"
                                     style="height: 100px">
                            </a>
                        </td>
                        <td>${t.price} <fmt:message key="main.currency"/></td>
                        <td class="text-center">${t.operationDate}</td>
                        <td>${t.state}</td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.user.role == 'ROLE_ADMIN'}">
                                    <a href="${path}/tickets/delete?ticketid=${t.id}"
                                       class="delete">
                                        <i class="material-icons"
                                           data-toggle="tooltip"
                                           title="<fmt:message key="manage.action.delete"/>">&#xE872;</i></a>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${t.state == 'ACTIVE'}">
                                        <a class="btn btn-warning"
                                           href="${path}/tickets/refund?ticketid=${t.id}">
                                            <fmt:message key="tickets.manage.refund"/></a>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <jsp:include page="/WEB-INF/jsp/pagination.jsp"/>
        </div>
    </div>
</div>
<script src="${path}/assets/js/func.js"></script>
</body>
</html>