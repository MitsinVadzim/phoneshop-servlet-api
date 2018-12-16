package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.interfaces.ICartService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {

    private OrderService orderService = OrderService.getInstance();

    @Override
    public void init(){

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = orderService.addOrder(request);
        if (id != null) {
            String path = request.getContextPath() + "/order/overview/" + id;
            response.sendRedirect(path);
        }else{
            request.setAttribute("message", "Order error");
            request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
    }
}
