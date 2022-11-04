<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="halls.manage.title"/></title>
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
                        <h2><b><fmt:message key="halls.manage.title"/></b></h2>
                    </div>
                    <div class="col-sm-6">
                        <a href="${path}/halls/create"
                           class="btn btn-success"><i class="material-icons">&#xE147;</i>
                            <span><fmt:message key="halls.manage.create"/></span></a>
                    </div>
                </div>
                <div class="mt-2">
                    <label for="itemsPerPage"><fmt:message key="main.itemsPerPage"/></label>
                    <select class="form-select-sm" id="itemsPerPage">
                        <option value="5" ${sessionScope.hallsPerPage == 5 ? 'selected' : ''}>5</option>
                        <option value="10" ${sessionScope.hallsPerPage == 10 ? 'selected' : ''}>10</option>
                        <option value="15" ${sessionScope.hallsPerPage == 15 ? 'selected' : ''}>15</option>
                    </select>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>No</th>
                    <th><fmt:message key="halls.manage.name"/></th>
                    <th><fmt:message key="halls.manage.address"/></th>
                    <th><fmt:message key="halls.manage.capacity"/></th>
                    <th><fmt:message key="manage.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="h" items="${halls}" varStatus="status">
                    <tr>
                        <td>${status.count + hallsPerPage * (page-1)}</td>
                        <td>${h.name}</td>
                        <td>${h.address}</td>
                        <td>${h.capacity}</td>
                        <td>
                            <a href="${path}/halls/edit?hallid=${h.id}" class="edit">
                                <i class="material-icons"
                                   data-toggle="tooltip"
                                   title="Edit">&#xE254;</i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <jsp:include page="/WEB-INF/jsp/pagination.jsp"/>
        </div>
    </div>
</div>
<script lang="javascript">
    $('#itemsPerPage').on('change', function () {
        $.get("${sessionScope.origin}", {itemsPerPage: $('#itemsPerPage').val()})
            .done(function () {
                location.reload();
            });
    });
</script>
</body>
</html>