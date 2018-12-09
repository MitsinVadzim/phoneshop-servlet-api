package com.es.phoneshop.web;

import com.es.phoneshop.service.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartPageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSessionCartService httpSessionCartService = new HttpSessionCartService(request);
        String[] arrQuantity = request.getParameterValues("quantity");
        String[] arrId = request.getParameterValues("productId");
        String[] arrMessage = new String[arrQuantity.length];
        for (int i = 0; i < arrQuantity.length; i++){
            if (!isInt(arrQuantity[i])){
                arrMessage[i] = "Insert the number";
            }else {
                if (Integer.parseInt(arrQuantity[i]) != 0) {
                    if (httpSessionCartService.add(Long.parseLong(arrId[i]), Integer.parseInt(arrQuantity[i]))) {
                        arrMessage[i] = "Product was added";
                    } else {
                        arrMessage[i] = "Not enough stock";
                    }
                }
            }
        }
        request.setAttribute("arrQuantity", arrQuantity);
        request.setAttribute("arrMessage", arrMessage);
        request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
    }

    private boolean isInt(String s){
        try{
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
