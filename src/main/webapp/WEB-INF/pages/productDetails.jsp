<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Created by IntelliJ IDEA.
  User: mitin
  Date: 18.11.2018
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product List</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
</header>
<main>
    <c:url var="productUrl" value="">

    </c:url>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
            </td>
            <td class="price">Price</td>
            <td>Stock</td>
        </tr>
        </thead>
        ${sessionScope.cart}
        <tr>
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
            </td>
            <td>${product.stock}</td>
            <form method="post" action="${productUrl}/phoneshop-servlet-api/products/${product.id}">
                <input type="text" value="${ not empty param.quantity ? param.quantity : 0}" name="quantity">
                <button type="submit" name="id", value="${product.id}"Order></button>
            </form>
            <p>${message}</p>
        </tr>
    </table>
    <c:forEach var="recentProduct" items="${recentProducts}">
        <td>
            <a href="${productUrl}/phoneshop-servlet-api/products/${recentProduct.id}">
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${recentProduct.imageUrl}">
            </a>
        </td>
        <p>${recentProduct.code}</p>
        <p>${recentProduct.price}</p>
    </c:forEach>
</main>
</body>
</html>
