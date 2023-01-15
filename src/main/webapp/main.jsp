<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">

<head>
    <title><fmt:message key="navbar.exhibitions"/></title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=
    https://fonts.googleapis.com/css?family=
    Inconsolata:400,500,600,700|Raleway:400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet">
    <!-- Vendor CSS Files -->
    <link href="assets/vendor/aos/aos.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="assets/css/style.css" rel="stylesheet">


    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<body style="background: rgba(186,196,179,0.6)">
<jsp:include page="WEB-INF/navbar.jsp"/>
<main id="main">

    <!-- ======= Works Section ======= -->
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <div id="sort_param" class="row m-0 mt-5 text-center">
                    <b class="fs-4 mb-2"> <fmt:message key="main.sort"/>:</b>
                    <a class="btn btn-outline-primary mb-2" href="${path}/main?sort=title"><fmt:message
                            key="main.sort.title"/></a>
                    <a class="btn btn-outline-primary mb-2" href="${path}/main?sort=price"><fmt:message
                            key="main.sort.price"/></a>
                    <a class="btn btn-outline-primary mb-2" href="${path}/main?sort=date"><fmt:message
                            key="main.sort.date"/></a>
                    <form class="border border-primary rounded p-2" method="get" action="main" role="form">
                        <label>
                            <b class="text-primary"><fmt:message key="main.filter.date.title"/></b>
                        </label>
                        <br/>
                        <label><fmt:message key="main.filter.date.from"/> </label>

                        <input type="date" class="datepicker-dropdown" name="from">
                        <br/>
                        <label><fmt:message key="main.filter.date.to"/> </label>

                        <input type="date" class="datepicker-dropdown" name="to">
                        <br/>
                        <input
                                class="btn btn-primary mt-2"
                                type="submit"
                                value="<fmt:message key="main.filter.date.button"/>">
                    </form>

                </div>
            </div>
            <div class="col-8">
                <div class="row text-center ">
                    <h2 class="h3 mt-3"><fmt:message key="main.label"/></h2>
                    <div class="col text-right">
                        <label for="itemsPerPage"><fmt:message key="main.itemsPerPage"/></label>
                        <select class="form-select-sm" id="itemsPerPage">
                            <option value="6" ${sessionScope.exhibitionsMainPage == 6 ? 'selected' : ''}>6</option>
                            <option value="12" ${sessionScope.exhibitionsMainPage == 12 ? 'selected' : ''}>12</option>
                            <option value="18" ${sessionScope.exhibitionsMainPage == 18 ? 'selected' : ''}>18</option>
                        </select>
                    </div>
                </div>
                <div class="row ">
                    <c:forEach var="e" items="${ex}">
                        <div class="item ${e.theme.name} col-sm-6 col-md-4 col-lg-4 mb-4">
                            <a href="${path}/exhibitions/show?exid=${e.id}"
                               class="item-wrap fancybox">
                                <div class="work-info">
                                    <h3>${e.title}</h3>
                                    <span>${e.startDate} - ${e.finishDate}</span>
                                    <p>${e.price} <fmt:message key="main.currency"/></p>
                                </div>
                                <img class="img-thumbnail "
                                     src="assets/img/${e.id}/${e.image}"
                                     onError="this.onerror=null;this.src='assets/img/alt.jpg';"
                                     alt="${e.title}"
                                     style="height: 350px; width: 400px">
                            </a>
                        </div>
                    </c:forEach>
                </div>
                <jsp:include page="/WEB-INF/jsp/pagination.jsp"/>
            </div>
            <div class="col">
                <div class="row m-0 mt-5 text-center">
                    <b class="fs-4 mb-2"> <fmt:message key="main.sort.theme"/>:</b>
                    <a class="btn btn-outline-primary mb-2" href="${path}/main"><fmt:message
                            key="main.sort.themes.all"/></a>
                    <c:forEach var="theme" items="${themes}">
                        <a class="btn btn-outline-primary mb-2"
                           href="${path}/main?topic=${theme.id}">${theme.name}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</main><!-- End #main -->

<script src="assets/js/func.js"></script>
</body>
</html>