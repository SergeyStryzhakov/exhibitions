<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<!DOCTYPE html>
<html lang=${lang}>
<head>
    <title><fmt:message key="themes.manage.create"/></title>
</head>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<body style="background: rgba(186,196,179,0.6)">
<div class="container">
    <div class="row mb-5 align-items-end">
        <div class="col-md-6" data-aos="fade-up">
            <h2><fmt:message key="themes.manage.create"/></h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 mb-5 mb-md-0" data-aos="fade-up">
            <form action="create" method="post" role="form">
                <div class="row gy-3">
                    <div class="col-md-12 form-group">
                        <label for="name"><fmt:message key="themes.manage.name"/></label>
                        <input type="text"
                               name="name"
                               class="form-control"
                               id="name"
                               required>
                    </div>
                    <div class="col-md-12 mt-0 form-group">
                        <input type="submit"
                               class="btn btn-primary d-block w-100"
                               value="<fmt:message key="create.button"/>">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>