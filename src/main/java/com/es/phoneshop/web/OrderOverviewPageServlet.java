package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.interfaces.IDao;
import com.es.phoneshop.model.deliveryMode.ArrayListDeliveryModeDao;
import com.es.phoneshop.model.deliveryMode.DeliveryMode;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {

    private IDao<Order, String> orderList;

    @Override
    public void init() throws ServletException {
        orderList = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = getIdFromURI(request);
        Order order = orderList.getElement(id);
        if (order != null){
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/overviewPage.jsp").forward(request, response);
        }else{
            throw new OrderNotFoundException("Order was not founded");
        }
    }

    private String getIdFromURI(final HttpServletRequest request) {
        String[] massStr = request.getRequestURI().split("/");
        return massStr[massStr.length - 1];
    }
}
