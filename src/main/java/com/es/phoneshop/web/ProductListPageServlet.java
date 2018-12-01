package com.es.phoneshop.web;

import com.es.phoneshop.logic.ProductDaoLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ProductDaoLogic productDaoLogic;


    @Override
    public void init() throws ServletException {
        // TODO what's the point of calling super.init() ?
        super.init();
        productDaoLogic = ProductDaoLogic.getInstance();
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        if (search == null) {
            search = "";
        }
        if (sort == null) {
            sort = "ascDescription";
        }

        request.setAttribute("products", productDaoLogic.findProducts(search, sort));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
