<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<!DOCTYPE html>
<html lang=${lang}>
<head>
    <title><fmt:message key="single.title"/></title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=https://fonts.googleapis.com/css?family=Inconsolata:400,500,600,700|Raleway:400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet">
    <!-- Vendor CSS Files -->
    <link href="${path}/assets/vendor/aos/aos.css" rel="stylesheet">
    <link href="${path}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${path}/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <!-- Template Main CSS File -->
    <link href="${path}/assets/css/style.css" rel="stylesheet">
</head>
<body style="background: rgba(186,196,179,0.6)">
<!-- ======= Navbar ======= -->
<jsp:include page="/WEB-INF/navbar.jsp"/>
<main id="main">
    <div class="container">
        <div class="row mb-4 mt-5 align-items-center">
            <div class="col-md-6" data-aos="fade-up">
                <h2 class="text-center">${ex.title}</h2>
            </div>
        </div>
    </div>
    <div class="site-section pb-0">
        <div class="container">
            <div class="row align-items-stretch">
                <div class="col-md-7" data-aos="fade-up">
                    <img src="${path}/assets/img/${ex.id}/${ex.image}"
                         onError="this.onerror=null;this.src='${path}/assets/img/alt.jpg';"
                         alt="Image"
                         class="img-fluid">
                    <p class="text-justify pt-3">${ex.description}</p>
                    <h4 class="h3 pt-3 mb-3 text-center"><fmt:message key="single.information"/>:</h4>
                    <ul class="list-unstyled list-line mb-5">
                        <li><fmt:message key="exhibition.start"/>: ${ex.startDate}</li>
                        <li><fmt:message key="exhibition.finish"/>: ${ex.finishDate}</li>
                        <li><fmt:message key="single.opentime"/>: ${ex.openTime} - ${ex.closeTime}</li>
                        <li><b><fmt:message key="exhibition.price"/>: ${ex.price} <fmt:message key="main.currency"/></b>
                        </li>
                    </ul>
                    <a class="btn btn-primary" href="${path}">Main page</a>
                    <button class="btn btn-primary" onclick="history.back();">Back</button>
                </div>
                <div class="col-md-5 ml-auto" data-aos="fade-up" data-aos-delay="100">
                    <div class="sticky-content">
                        <h4 class="h3 mb-3 text-center"><fmt:message key="single.where"/>:</h4>
                        <c:forEach var="hall" items="${ex.halls}">
                            <form class="border border-primary m-3 p-3"
                                  action="${path}/tickets/buy"
                                  method="post">
                                <input type="hidden" name="hid" value="${hall.id}">
                                <input type="hidden" name="exid" value="${ex.id}">
                                <input type="hidden" name="price" value="${ex.price}">
                                <c:set var="availableTicket" scope="request"
                                       value="${requestScope.available.get(hall.id)}"/>
                                <ul class="list-unstyled list-line">
                                    <li><b><fmt:message key="single.hall"/> </b>${hall.name}</li>
                                    <li><b><fmt:message key="single.address"/> </b>${hall.address}</li>
                                    <c:choose>
                                        <c:when test="${availableTicket > 0}">
                                            <li><b><fmt:message key="single.available"/>:</b>
                                                    ${availableTicket}/${hall.capacity}</li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><fmt:message key="single.available"/>:
                                                <fmt:message key="single.noticket"/></li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>

                                <c:choose>
                                    <c:when test="${sessionScope.user != null}">
                                        <c:choose>
                                            <c:when test="${ex.price > sessionScope.user.balance}">
                                                <div class="text-danger text-center"><fmt:message
                                                        key="single.fillaccount"/></div>
                                            </c:when>
                                            <c:when test="${availableTicket > 0}">
                                                <div class="text-center">
                                                    <input class="btn btn-primary mb-2 text-center"
                                                           type="submit"
                                                           value="<fmt:message key="single.buyticket"/>">
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="text-danger text-center"><fmt:message
                                                        key="single.noticket"/></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-primary text-center"><fmt:message key="single.needlogin"/></p>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </c:forEach>


                    </div>
                </div>
            </div>
        </div>
    </div>
</main><!-- End #main -->

<!-- Vendor JS Files -->
<script src="${path}/assets/vendor/aos/aos.js"></script>
<script src="${path}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${path}/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
<script src="${path}/assets/vendor/swiper/swiper-bundle.min.js"></script>

<!-- Template Main JS File -->
<script src="${path}/assets/js/main.js"></script>

</body>

</html>