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

    <!-- Template Main CSS File -->
    <link href="assets/css/style.css" rel="stylesheet">
    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</head>
<body style="background: rgba(186,196,179,0.6)">
<jsp:include page="WEB-INF/navbar.jsp"/>
<main id="main">

    <!-- ======= Works Section ======= -->
    <section class="section ">
        <div class="container">
            <div class="row mb-5 align-items-center">
                <div class="col-md-12 col-lg-6 mb-4 mb-lg-0">
                    <p class="h3 mb-0"><fmt:message key="main.label"/>:</p>
                </div>
                <div class="col-md-12 col-lg-6 text-center">

                    <div id="filters" class="filters">
                        <b> <fmt:message key="main.sort.theme"/>:</b>
                        <a href="${path}/main"><fmt:message key="main.sort.themes.all"/></a>
                        <c:forEach var="theme" items="${themes}">
                            <a href="${path}/main?topic=${theme.id}">${theme.name}</a>
                        </c:forEach>
                    </div>
                </div>
                <div class="mb-2">
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
                                 style="height: 350px; width: 350px">
                        </a>
                    </div>
                </c:forEach>
            </div>
            <jsp:include page="/WEB-INF/jsp/pagination.jsp"/>
        </div>
    </section><!-- End Works Section -->
</main><!-- End #main -->
<!-- Template Main JS File -->
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