package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.service.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String operation = pathParts[2];
        if (operation.equals("delete")) {
            Long id = getIdFromURI(request);
            HttpSessionCartService httpSessionCartService = new HttpSessionCartService(request);
            httpSessionCartService.delete(id);
        }
        String path = request.getContextPath() + "/cart";
        response.sendRedirect(path);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getContextPath() + "/cart";
        response.sendRedirect(path);
    }

    private Long getIdFromURI(final HttpServletRequest request) {
        String[] massStr = request.getRequestURI().split("/");
        String string = massStr[massStr.length - 2];
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException ex) {
            throw new ProductNotFoundException("Product was not founded.");
        }
    }
}
