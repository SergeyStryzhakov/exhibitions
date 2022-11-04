<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<!DOCTYPE html>
<html lang=${lang}>
<head>
    <title><fmt:message key="user.manage.title"/></title>
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
                        <h2><b><fmt:message key="user.manage.title"/></b></h2>
                    </div>
                </div>
                <div class="mt-2">
                    <label for="itemsPerPage"><fmt:message key="main.itemsPerPage"/></label>
                    <select class="form-select-sm" id="itemsPerPage">
                        <option value="5" ${sessionScope.usersPerPage == 5 ? 'selected' : ''}>5</option>
                        <option value="10" ${sessionScope.usersPerPage == 10 ? 'selected' : ''}>10</option>
                        <option value="15" ${sessionScope.usersPerPage == 15 ? 'selected' : ''}>15</option>
                    </select>
                </div>
            </div>
            ${page}
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>No</th>
                    <th><fmt:message key="user.registration.login"/></th>
                    <th><fmt:message key="user.registration.firstname"/></th>
                    <th><fmt:message key="user.registration.lastname"/></th>
                    <th><fmt:message key="user.registration.email"/></th>
                    <th><fmt:message key="navbar.balance"/></th>
                    <th><fmt:message key="user.manage.role"/></th>
                    <th><fmt:message key="manage.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${sessionScope.users}" varStatus="status">
                    <tr>
                        <td>${status.count + usersPerPage * (page-1)}</td>
                        <td>${u.login}</td>
                        <td>${u.firstName}</td>
                        <td>${u.lastName}</td>
                        <td>${u.email}</td>
                        <td>${u.balance}</td>
                        <td>${u.role}</td>
                        <td>
                            <a href="${path}/users/edit?uid=${u.id}" class="edit">
                                <i class="material-icons"
                                   data-toggle="tooltip"
                                   title="<fmt:message key="manage.action.edit"/>">&#xE254;</i></a>
                            <a href="${path}/users/delete?uid=${u.id}" class="delete">
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