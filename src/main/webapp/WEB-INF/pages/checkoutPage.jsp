<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header_footer title="Checkout" isShowMiniCart="false">
    ${message}
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
            </td>
            <td class="Price">Price</td>
            <td>Quantity</td>
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
            </tr>
        </c:forEach>
        </thead>
    </table>
    <form method="post">
        Phone: <input type="text" name="phone" placeholder="phone number" required>
        <br>
        Delivery mode:
        <select name="mode">
            <c:forEach var="modeItem" items="${modeList}">
                <option value="${modeItem.id}">
                        ${modeItem.mode} ${modeItem.cost}$
                </option>
            </c:forEach>
        </select>
        <%--<input type="hidden" name="mode" value="Courier">--%>
        <br>
        Delivery date:
        <input type="text" name="date" placeholder="date" required>
        <br>
        Delivery address:
        <input type="text" name="address" required>
        <br>
        Payment method: money of courier
        <input type="hidden" name="method" value="money of courier">
        <br>
        <button>Order</button>
    </form>
</t:header_footer>
