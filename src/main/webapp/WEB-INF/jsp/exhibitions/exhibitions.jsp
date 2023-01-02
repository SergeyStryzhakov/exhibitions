<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="exhibition.manage.title"/></title>
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
                        <h2><b><fmt:message key="exhibition.manage.title"/></b></h2>
                    </div>

                    <div class="col-sm-6">
                        <a href="${path}/exhibitions/create"
                           class="btn btn-success"><i class="material-icons">&#xE147;</i>
                            <span><fmt:message key="exhibition.manage.create"/></span></a>
                    </div>
                </div>
                <div class="mt-2">
                    <label for="itemsPerPage"><fmt:message key="main.itemsPerPage"/></label>
                    <select class="form-select-sm" id="itemsPerPage">
                        <option value="5" ${sessionScope.exhibitionsPerPage == 5 ? 'selected' : ''}>5</option>
                        <option value="10" ${sessionScope.exhibitionsPerPage == 10 ? 'selected' : ''}>10</option>
                        <option value="15" ${sessionScope.exhibitionsPerPage == 15 ? 'selected' : ''}>15</option>
                    </select>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>No</th>
                    <th><fmt:message key="exhibition.title"/></th>
                    <th><fmt:message key="exhibition.description"/></th>
                    <th><fmt:message key="exhibition.theme"/></th>
                    <th><fmt:message key="exhibition.halls"/></th>
                    <th><fmt:message key="exhibition.start"/></th>
                    <th><fmt:message key="exhibition.finish"/></th>
                    <th><fmt:message key="exhibition.open"/></th>
                    <th><fmt:message key="exhibition.close"/></th>
                    <th><fmt:message key="exhibition.price"/></th>
                    <th><fmt:message key="exhibition.image"/></th>
                    <th><fmt:message key="exhibition.state"/></th>
                    <th><fmt:message key="manage.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="e" items="${ex}" varStatus="status">
                    <tr>
                        <td>${status.count + exhibitionsPerPage * (page-1)}</td>
                        <td>${e.title}</td>
                        <c:set var="desc" value="${e.description}"/>
                        <td><c:out value="${fn:substring(desc,0,10)}"/></td>
                        <td>${e.theme.name}</td>
                        <td>
                            <c:forEach var="hall" items="${e.halls}">
                                <p>${hall.name}</p>
                            </c:forEach></td>
                        <td>${e.startDate}</td>
                        <td>${e.finishDate}</td>
                        <td>${e.openTime}</td>
                        <td>${e.closeTime}</td>
                        <td>${e.price} <fmt:message key="main.currency"/></td>
                        <td><img class="img-thumbnail"
                                 src="${path}/assets/img/${e.id}/${e.image}"
                                 onError="this.onerror=null;this.src='${path}/assets/img/alt.jpg';"
                                 alt="${e.title}"
                                 style="height: 100px"></td>
                        <td>${e.state}</td>
                        <td>
                            <a href="${path}/exhibitions/edit?exid=${e.id}" class="edit">
                                <i class="material-icons"
                                   data-toggle="tooltip"
                                   title="Edit">&#xE254;</i></a>
                            <a href="${path}/exhibitions/delete?exid=${e.id}" exid="${e.id}"
                               class="delete">
                                <i class="material-icons"
                                   data-toggle="tooltip"
                                   title="Delete">&#xE872;</i></a>
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