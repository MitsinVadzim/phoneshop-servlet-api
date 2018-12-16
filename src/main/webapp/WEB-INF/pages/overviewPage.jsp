<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header_footer title="Overview" isShowMiniCart="false">
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
        <c:forEach var="cartItem" items="${order.cart.cartItemList}" varStatus="loop">
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
        <tr>
            <td></td>
            <td>

            </td>
            <td class="Price">
                The price of all goods:
            </td>
            <td>${order.cart.totalPrice}</td>
        </tr>
        </thead>
    </table>
    Phone: ${order.phoneNumber}
    <br>
    Delivery mode: ${order.deliveryMode}
    <br>
    Delivery date: ${order.deliveryDate}
    <br>
    Cost of delivery: ${order.costOfDeliver}
    <br>
    Delivery address: ${order.deliveryAddress}
    <br>
    Payment method: ${order.paymentMethod}
    <br>
    Total Price: ${order.cart.totalPrice + order.costOfDeliver}
</t:header_footer>
