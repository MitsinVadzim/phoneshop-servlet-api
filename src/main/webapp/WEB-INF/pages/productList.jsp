<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
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
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form method="get" name="products">
        <input type="text" name="search" value="<%= request.getParameter("search") %>">
        <input type="submit" value="Search">
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <a href="<%="http://localhost:8080/phoneshop-servlet-api/products"+"?"+"search="+request.getParameter("search")+"&sort="+"ascDescription"%>">asc</a>
                <a href="<%="http://localhost:8080/phoneshop-servlet-api/products"+"?"+"search="+request.getParameter("search")+"&sort="+"descDescription"%>">desc</a>

            </td>
            <td class="price">
                Price
                <a href="<%="http://localhost:8080/phoneshop-servlet-api/products"+"?"+"search="+request.getParameter("search")+"&sort="+"ascPrice"%>">asc</a>
                <a href="<%="http://localhost:8080/phoneshop-servlet-api/products"+"?"+"search="+request.getParameter("search")+"&sort="+"descPrice"%>">desc</a>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <a href="http://localhost:8080/phoneshop-servlet-api/products/${product.id}">
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </a>
                </td>
                <td>${product.description}</td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>