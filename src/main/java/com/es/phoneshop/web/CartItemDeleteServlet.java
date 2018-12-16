package com.es.phoneshop.web;

import com.es.phoneshop.interfaces.ICartService;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.service.HttpSessionCartService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {

    private ICartService httpSessionCartService;

    @Override
    public void init() {
        httpSessionCartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String operation = pathParts[2];
        if (operation.equals("delete")) {
            Long id = getIdFromURI(request);
            httpSessionCartService.delete(id, request.getSession());
        }
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
