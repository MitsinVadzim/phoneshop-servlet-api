package com.es.phoneshop.service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.interfaces.IDao;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderService {

    private IDao<Order, String> orderList = ArrayListOrderDao.getInstance();

    private static OrderService instance;

    private OrderService() {

    }

    public synchronized static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderService();
                }
            }
        }
        return instance;
    }

    public String addOrder(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            Order order = new Order();
            order.setCart(cart);
            order.setCostOfDeliver(new BigDecimal(request.getParameter("cost")));
            order.setDeliveryAddress(request.getParameter("address"));
            order.setDeliveryDate(request.getParameter("date"));
            order.setDeliveryMode(request.getParameter("mode"));
            order.setPaymentMethod(request.getParameter("method"));
            order.setPhoneNumber(request.getParameter("phone"));
            String id = UUID.randomUUID().toString();
            order.setId(id);
            orderList.save(order);
            return id;
        } else {
            return null;
        }
    }
}
