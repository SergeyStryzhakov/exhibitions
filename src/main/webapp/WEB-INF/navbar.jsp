<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<html>
<head>
    <title><fmt:message key="navbar.exhibitions"/></title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,600">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${path}/assets/css/navbar.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="${path}" class="navbar-brand">
        <b><fmt:message key="navbar.exhibitions"/></b>
    </a>
    <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
        <div class="navbar-nav">
            <c:if test="${sessionScope.user.role=='ROLE_ADMIN'}">
                <a href="${path}/exhibitions/show"
                   class="nav-item nav-link"><fmt:message key="navbar.exhibitions"/>
                </a>
                <a href="${path}/halls/show"
                   class="nav-item nav-link"><fmt:message key="navbar.halls"/>
                </a>
                <a href="${path}/themes/show"
                   class="nav-item nav-link "><fmt:message key="navbar.themes"/>
                </a>
                <a href="${path}/tickets/show"
                   class="nav-item nav-link"><fmt:message key="navbar.tickets"/>
                </a>
                <a href="${path}/users/show"
                   class="nav-item nav-link"><fmt:message key="navbar.users"/>
                </a>
            </c:if>
            <c:if test="${sessionScope.user.role=='ROLE_USER'}">
                <a href="${path}/tickets/show"
                   class="nav-item nav-link"><fmt:message key="navbar.mytickets"/>
                </a>
                <div class="nav-item nav-link">
                    <i class="text-info">
                        <fmt:message key="navbar.balance"/>:
                            ${sessionScope.user.balance}
                        <fmt:message key="main.currency"/> </i>
                </div>
            </c:if>
        </div>
        <div class="navbar-nav ml-auto">
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="${path}/login?action=logout" class="nav-item nav-link"><i
                            class="fa fa-user-o"></i> <fmt:message key="navbar.logout"/>, <i
                            class="text-primary">${sessionScope.user.login}</i></a>
                </c:when>
                <c:otherwise>
                    <div class="nav-item dropdown login-dropdown">
                        <a href="#" data-toggle="dropdown"
                           class="nav-item nav-link dropdown-toggle">
                            <i class="fa fa-user-o"></i>
                            <fmt:message key="user.registration.login"/></a>
                        <div class="dropdown-menu">
                            <form class="form-inline login-form" action="${path}/login" method="post">
                                <div class="input-group">
                                    <div class="input-group-prepend">
								<span class="input-group-text">
									<span class="fa fa-user"></span>
								</span>
                                    </div>
                                    <input type="text" class="form-control"
                                           placeholder="<fmt:message key="user.registration.login"/>"
                                           name="login"
                                           required>
                                </div>
                                <div class="input-group">
                                    <div class="input-group-prepend">
								<span class="input-group-text">
									<i class="fa fa-lock"></i>
								</span>
                                    </div>
                                    <input type="password" class="form-control"
                                           placeholder="<fmt:message key="user.registration.password"/>"
                                           name="password"
                                           required>
                                </div>
                                <button type="submit"
                                        class="btn btn-primary">
                                    <fmt:message key="navbar.login.button"/>
                                </button>
                            </form>
                            <div class="pt-3">
                                <i><fmt:message key="navbar.noaccount"/></i>
                                <a href="${path}/register">
                                    <fmt:message key="user.registration.button"/>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <select class="form-select-sm" id="lang">
                <option value="en" ${lang == 'en' ? 'selected' : ''}>EN</option>
                <option value="ua" ${lang == 'ua' ? 'selected' : ''}>UA</option>
            </select>
        </div>
    </div>
</nav>
<script lang="javascript">
    $('#lang').on('change', function () {
        $.get("${path}/main", {lang: $('#lang').val()})
            .done(function () {
                location.reload();
            });
    });
</script>
</body>
</html>