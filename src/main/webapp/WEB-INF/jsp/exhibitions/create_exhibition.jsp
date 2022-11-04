<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<!DOCTYPE html>
<html lang=${lang}>
<head>
    <title><fmt:message key="create.main.title"/></title>
</head>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<body style="background: rgba(186,196,179,0.6)">
<div class="container">

    <div class="row mb-5 align-items-end">
        <div class="col-md-6" data-aos="fade-up">
            <h2><fmt:message key="create.main.title"/></h2>
        </div>

    </div>

    <div class="row">
        <div class="col-md-6 mb-5 mb-md-0" data-aos="fade-up">
            <form action="create" method="post" role="form" enctype="multipart/form-data">
                <div class="row gy-3">
                    <div class="col-md-12 form-group">
                        <label for="title"><fmt:message key="exhibition.title"/></label>
                        <input type="text" name="title" class="form-control" id="title" value=""
                               required>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="description"><fmt:message key="exhibition.description"/></label>
                        <textarea class="form-control" name="description" id="description" cols="10" rows="3"
                                  required></textarea>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="theme"><fmt:message key="exhibition.theme"/></label>
                        <select name="theme" class="form-control" id="theme">
                            <c:forEach var="t" items="${themes}">
                                <option value="${t.id}">${t.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="halls"><fmt:message key="exhibition.halls"/></label>
                        <ul class="list-group" id="halls">
                            <c:forEach var="hall" items="${halls}">
                                <li class="list-group-item">
                                    <input class="form-check-input"
                                           type="checkbox"
                                           name="halls"
                                           value="${hall.id}">
                                        ${hall.name}
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="start-date"><fmt:message key="exhibition.start"/></label>
                        <input type="date" class="form-control" name="start-date" id="start-date"
                               value="" required>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="finish-date"><fmt:message key="exhibition.finish"/></label>
                        <input type="date" class="form-control" name="finish-date" id="finish-date"
                               value="" required>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="open-time"><fmt:message key="exhibition.open"/></label>
                        <input type="time" class="form-control" name="open-time" id="open-time"
                               value="" required>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="close-time"><fmt:message key="exhibition.close"/></label>
                        <input type="time" class="form-control" name="close-time" id="close-time"
                               value="" required>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="price"><fmt:message key="exhibition.price"/></label>
                        <input type="number" class="form-control" name="price" id="price" value=""
                               required>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="state"><fmt:message key="exhibition.state"/></label>
                        <select class="form-control" name="state" id="state" required>
                            <c:forEach var="state" items="${states}">
                                <option value="${state}">${state}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="image" class="form-label"><fmt:message key="exhibition.image.select"/></label>
                        <input class="form-control" type="file" id="image" accept="image/*" name="image">
                    </div>
                    <div class="col-md-12 my-3">
                        <div class="error-message"></div>
                    </div>

                    <div class="col-md-12 mt-0 form-group">
                        <input type="submit" class="btn btn-primary d-block w-100"
                               value="<fmt:message key="create.button"/>">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>