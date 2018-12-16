<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<t:header_footer title="ProductList" isShowMiniCart="true">
    <form method="get" name="products">
        <input type="text" name="search" value="${param.search}">
        <input type="submit" value="Search">
    </form>
    <table>
        <thead>
        <tr>
            <c:url var="nextUrl" value="">
                <c:forEach items="${param}" var="entry">
                    <c:if test="${entry.key != 'sort'}">
                        <c:param name="${entry.key}" value="${entry.value}"/>
                    </c:if>
                </c:forEach>
            </c:url>
            <td>Image</td>
            <td>
                Description
                <a href="${nextUrl}&sort=ascDescription">asc</a>
                <a href="${nextUrl}&sort=descDescription">desc</a>
            </td>
            <td class="price">
                Price
                <a href="${nextUrl}&sort=ascPrice">asc</a>
                <a href="${nextUrl}&sort=descPrice">desc</a>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <a href="${productUrl}/phoneshop-servlet-api/products/${product.id}">
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
</t:header_footer>