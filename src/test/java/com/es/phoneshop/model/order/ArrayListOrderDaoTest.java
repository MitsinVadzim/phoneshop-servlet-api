package com.es.phoneshop.model.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.interfaces.IDao;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDaoTest {
    private final static String WRONG_ID = "FDASFGADSG";
    private final static String ID = "qwerty";
    private final static IDao<Order, String> orderDao = ArrayListOrderDao.getInstance();
    private Order order = new Order();

    @Before
    public void setup() {
        order.setPhoneNumber("1234");
        order.setDeliveryAddress("a");
        order.setPaymentMethod("method");
        order.setDeliveryMode("mode");
        order.setCostOfDeliver(new BigDecimal(10));
        order.setDeliveryDate("Tomorrow");
        order.setId(ID);
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem = new CartItem(5, new Product());
        Cart cart = new Cart();
        cart.addToCart(cartItem);
        order.setCart(cart);
        orderDao.save(order);
    }

    @Test
    public void getOrderInstanceTest() {
        ArrayListOrderDao instance = ArrayListOrderDao.getInstance();
        Assert.assertNotNull(instance);
    }

    @Test(expected = OrderNotFoundException.class)
    public void ThrowOrderNotFoundExceptionTest(){
        orderDao.getElement(WRONG_ID);
    }

    @Test
    public void GetCorrectOrderTest(){
        orderDao.getElement(ID);
        Assert.assertEquals(orderDao.getElement(ID).getId(), order.getId());
    }
}
