<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content"/>
<div class="clearfix">
    <div class="hint-text"><fmt:message key="main.pagination"/>
        <b>${pagination['currentSize']} </b><fmt:message key="main.pagination.outof"/>
        <b>${pagination['maxSize']} </b><fmt:message key="main.pagination.entries"/></div>
    <ul class="pagination">
        <c:forEach begin="1" end="${pagination['pageCount']}" varStatus="loop">
            <li class="page-item ${page == loop.index ? 'active' : ''}"><a
                    href="?page=${loop.index}"
                    class="page-link">${loop.index}</a></li>
        </c:forEach>
    </ul>
</div>
