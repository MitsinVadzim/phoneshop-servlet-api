package com.es.phoneshop.web;

import com.es.phoneshop.interfaces.ICartService;
import com.es.phoneshop.service.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private ICartService httpSessionCartService;

    @Override
    public void init() {
        httpSessionCartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] arrQuantity = request.getParameterValues("quantity");
        String[] arrId = request.getParameterValues("productId");
        Map<Long, String> mapErrors = new HashMap<>();
        for (int i = 0; i < arrQuantity.length; i++) {
            if (!httpSessionCartService.isInt(arrQuantity[i])) {
                mapErrors.put(Long.parseLong(arrId[i]), "Insert the number");
            } else {
                if (Integer.parseInt(arrQuantity[i]) > 0) {
                    if (!httpSessionCartService.add(Long.parseLong(arrId[i]), Integer.parseInt(arrQuantity[i]), request.getSession())) {
                        mapErrors.put(Long.parseLong(arrId[i]), "Not enough stock");
                    }
                }else{
                    mapErrors.put(Long.parseLong(arrId[i]), "Incorrect number");
                }
            }
        }
        if (mapErrors.isEmpty()){
            response.sendRedirect(request.getRequestURI() + "?message=Cart successfully changed");
        }else{
            request.setAttribute("arrQuantity", arrQuantity);
            request.setAttribute("mapErrors", mapErrors);
            request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
    }
}
