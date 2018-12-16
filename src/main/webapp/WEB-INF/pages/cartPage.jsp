<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header_footer title="Cart" isShowMiniCart="false">
    <a href="${pageContext.servletContext.contextPath}/checkout">Checkout</a>
    <br>
    <c:if test="${not empty param.message}">
        <p>${param.message}</p>
    </c:if>
    <form method="post">
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
            </td>
            <td class="Price">Price</td>
            <td>Quantity</td>
            <td>Stock</td>
            <td>Update Field</td>
            <td>Delete Field</td>
        </tr>
        <c:url var="productUrl" value="">
        </c:url>
        <c:forEach var="cartItem" items="${sessionScope.cart.cartItemList}" varStatus="loop">
            <tr>
                <td>
                    <a href="${productUrl}/phoneshop-servlet-api/products/${cartItem.product.id}">
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                    </a>
                </td>
                <td>${cartItem.product.description}</td>
                <td class="price">
                    <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                      currencySymbol="${cartItem.product.currency.symbol}"/>
                </td>
                <td>
                    ${cartItem.quantity}
                </td>
                <td>${cartItem.product.stock}</td>
                <td>
                    <input type="text" value="${cartItem.quantity}" name="quantity">
                    <input type="hidden" name="productId" value="${cartItem.product.id}">
                    <c:if test="${not empty mapErrors[cartItem.product.id]}">
                        <p>${mapErrors[cartItem.product.id]}</p>
                    </c:if>
                    <%--<p>${arrMessage[loop.index]}</p>--%>
                </td>
                <td>
                    <%--<form method="post" action="cart/${cartItem.product.id}/delete">--%>
                        <button formaction="cart/${cartItem.product.id}/delete">Delete</button>
                    <%--</form>--%>
                </td>
            </tr>
        </c:forEach>
        </thead>
    </table>
        <button type="submit">Update Cart</button>
    </form>
</t:header_footer>