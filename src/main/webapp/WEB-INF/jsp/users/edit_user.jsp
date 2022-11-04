<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<!DOCTYPE html>
<html lang=${lang}>
<head>
    <title><fmt:message key="user.manage.title"/></title>
</head>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<body style="background: rgba(186,196,179,0.6)">
<div class="container mt-5">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center card">
            <h1 class="text-center mt-4"><fmt:message key="user.manage.edit.title"/></h1>
            <div class="card-body">
                <form action="edit" method="POST">
                    <input type="hidden" name="uid" value="${requestScope.editUser.id}">
                    <div class="form-group">
                        <label class="fs-5 font-weight-bold">
                            <fmt:message key="user.registration.login"/>
                        </label>
                        <input
                                type="text"
                                name="login"
                                value="${requestScope.editUser.login}"
                                class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label class="fs-5 font-weight-bold">
                            <fmt:message key="user.registration.firstname"/>
                        </label>
                        <input
                                type="text"
                                name="first-name"
                                value="${requestScope.editUser.firstName}"
                                class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label class="fs-5 font-weight-bold">
                            <fmt:message key="user.registration.lastname"/>
                        </label>
                        <input
                                type="text"
                                name="last-name"
                                value="${requestScope.editUser.lastName}"
                                class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label class="fs-5 font-weight-bold">
                            <fmt:message key="user.registration.email"/>
                        </label>
                        <input
                                type="email"
                                name="email"
                                value="${requestScope.editUser.email}"
                                class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label class="fs-5 font-weight-bold">
                            <fmt:message key="navbar.balance"/>
                        </label>
                        <input
                                type="number"
                                name="balance"
                                value="${requestScope.editUser.balance}"
                                class="form-control"/>
                    </div>

                    <div class="form-group">
                        <label class="fs-5 font-weight-bold">
                            <fmt:message key="user.manage.role"/>
                        </label>
                        <select name="role" class="form-control">
                            <c:forEach var="role" items="${requestScope.roles}">
                                <option
                                        value="${role}"
                                    ${requestScope.editUser.role == role ? 'selected': ''}>${role}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-12 mb-3 form-group">
                        <input type="submit"
                               class="btn btn-primary d-block w-100"
                               value="<fmt:message key="edit.button"/>">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>