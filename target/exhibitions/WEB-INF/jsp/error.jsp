<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error</title>
</head>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<body style="background: rgba(186,196,179,0.6)">
<div class="container mt-5">
    <div class="text-center">
        <img src="${pageContext.request.contextPath}/assets/img/sad.jpg" alt="SAD" style="height: 100px">
    </div>
    <c:choose>
        <c:when test="${sessionScope.errorMessage != null}">
            <div class="h1 text-center text-danger">${sessionScope.errorMessage}</div>
        </c:when>
        <c:otherwise>
            <div class="h1 text-center">Status code: <i
                    class="text-danger">${requestScope.get('javax.servlet.error.status_code')}</i>
            </div>
            <div class="h4 text-center">Message: ${requestScope.get('javax.servlet.error.message')}</div>
            <div class="h4 text-center">Request_uri: ${requestScope.get("javax.servlet.error.request_uri")}</div>
            <br>
            <ul>
                <c:forEach items="${requestScope}" var="p">
                    <li>${p.key} -> ${p.value}</li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
    <div class="h4 text-center">
        <a class="btn btn-primary mt-5"
           href="${sessionScope.get("origin")}">
            Back</a>
        <a class="btn btn-primary mt-5"
           href="${pageContext.request.contextPath}">
            Home</a>
    </div>
</div>
</body>
</html>
