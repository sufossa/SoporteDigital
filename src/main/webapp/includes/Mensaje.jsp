<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.error != null}">
    <div class="alert alert-danger mt-1" role="alert">
        ${sessionScope.error}
    </div>
    <c:remove var="error" scope="session" />
</c:if>

<c:if test="${sessionScope.success != null}">
    <div class="alert alert-success mt-1" role="alert">
        ${sessionScope.success}
    </div>
    <c:remove var="success" scope="session" />
</c:if>
