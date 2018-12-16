<%@tag description="Common part of pages" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" required="true" fragment="false" %>
<%@attribute name="isShowMiniCart" required="true" fragment="false" %>
<html>
<head>
    <title>
        ${title}
    </title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
    <c:if test="${isShowMiniCart == true}">
        <a href="${pageContext.servletContext.contextPath}/cart">Cart: ${sessionScope.cart.totalPrice}</a>
    </c:if>
</header>
<main>
    <c:url var="productUrl" value="">
    </c:url>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <a href="${productUrl}/phoneshop-servlet-api/cart/minicart">Minicart</a>
    <jsp:doBody/>
</main>
</body>
</html>