<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="halls.manage.title"/></title>
</head>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<body style="background: rgba(186,196,179,0.6)">
<div class="container">

    <div class="row mb-5 align-items-end">
        <div class="col-md-6" data-aos="fade-up">
            <h2><fmt:message key="halls.manage.edit.title"/></h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 mb-5 mb-md-0" data-aos="fade-up">
            <form action="edit" method="post" role="form">
                <input type="hidden" name="hallid" value="${hall.id}">
                <div class="row gy-3">
                    <div class="col-md-12 form-group">
                        <label for="name">
                            <fmt:message key="halls.manage.name"/></label>
                        <input type="text"
                               name="name"
                               class="form-control"
                               id="name"
                               value="${hall.name}"
                               required>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="address">
                            <fmt:message key="halls.manage.address"/></label>
                        <input type="text"
                               name="address"
                               class="form-control"
                               id="address"
                               value="${hall.address}"
                               required>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="capacity">
                            <fmt:message key="halls.manage.capacity"/></label>
                        <input type="number"
                               name="capacity"
                               class="form-control"
                               id="capacity"
                               value="${hall.capacity}"
                               required>
                    </div>
                    <div class="col-md-12 mt-0 form-group">
                        <input type="submit"
                               class="btn btn-primary d-block w-100"
                               value="<fmt:message key="edit.button"/>">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>