<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<t:header_footer title="Product Details" isShowMiniCart="true">
    <a href="${pageContext.servletContext.contextPath}/checkout">Checkout</a>
    <br>
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
            <form method="post" action="${homeUrl}/phoneshop-servlet-api/products/${product.id}">
                <input type="text" value="${ not empty param.quantity ? param.quantity : 1}" name="quantity">
                <button type="submit" name="id", value="${product.id}">Order</button>
            </form>
            <p>${message}</p>
        </tr>
    </table>
    <c:forEach var="recentProduct" items="${recentProducts}">
        <td>
            <a href="${homeUrl}/phoneshop-servlet-api/products/${recentProduct.id}">
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${recentProduct.imageUrl}">
            </a>
        </td>
        <p>${recentProduct.code}</p>
        <p>${recentProduct.price}</p>
    </c:forEach>
</t:header_footer>
