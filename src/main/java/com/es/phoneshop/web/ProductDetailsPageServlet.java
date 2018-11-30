package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init() throws ServletException {
        super.init();
        arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        Long id = getIdFromURI(request);
        Product myProduct = arrayListProductDao.getProduct(id);
        request.setAttribute("product", myProduct);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    private Long getIdFromURI(final HttpServletRequest request) {
        String[] massStr = request.getRequestURI().split("/");
        return Long.parseLong(massStr[massStr.length - 1]);
    }
}
