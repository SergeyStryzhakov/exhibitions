<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="themes.manage.title"/></title>
    <link rel="stylesheet" href="${path}/assets/css/show.css">
</head>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<body style="background: rgba(186,196,179,0.6)">
<div class="container-xl">
    <div class="table-responsive">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2><b><fmt:message key="themes.manage.title"/></b></h2>
                    </div>
                    <div class="col-sm-6">
                        <a href="${path}/themes/create"
                           class="btn btn-success"><i class="material-icons">&#xE147;</i>
                            <span><fmt:message key="themes.manage.create"/></span></a>
                    </div>
                </div>
                <div class="mt-2">
                    <label for="itemsPerPage"><fmt:message key="main.itemsPerPage"/></label>
                    <select class="form-select-sm" id="itemsPerPage">
                        <option value="5" ${sessionScope.themesPerPage == 5 ? 'selected' : ''}>5</option>
                        <option value="10" ${sessionScope.themesPerPage == 10 ? 'selected' : ''}>10</option>
                        <option value="15" ${sessionScope.themesPerPage == 15 ? 'selected' : ''}>15</option>
                    </select>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>No</th>
                    <th class="text-center"><fmt:message key="themes.manage.name"/></th>
                    <th><fmt:message key="manage.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="t" items="${themes}" varStatus="status">
                    <tr>
                        <td>${status.count + themesPerPage * (page-1)}</td>
                        <td class="text-center">${t.name}</td>

                        <td>
                            <a href="${path}/themes/edit?themeid=${t.id}" class="edit">
                                <i class="material-icons"
                                   data-toggle="tooltip"
                                   title="<fmt:message key="manage.action.edit"/>">&#xE254;</i></a>
                            <a href="${path}/themes/delete?themeid=${t.id}" class="delete">
                                <i class="material-icons"
                                   data-toggle="tooltip"
                                   title="<fmt:message key="manage.action.delete"/>">&#xE872;</i></a>
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