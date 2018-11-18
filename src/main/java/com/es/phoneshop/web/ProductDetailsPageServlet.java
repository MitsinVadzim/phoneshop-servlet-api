package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = getIdFromURI(request);
        Product myProduct = getSampleProduct().getProduct(id);
        if (myProduct == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("product", myProduct);
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);

        }
    }

    private ArrayListProductDao getSampleProduct() {

        return ArrayListProductDao.getInstance();
    }

    private Long getIdFromURI(HttpServletRequest request) {
        String[] massStr = request.getRequestURI().split("/");
        return Long.parseLong(massStr[massStr.length - 1]);
    }
}
