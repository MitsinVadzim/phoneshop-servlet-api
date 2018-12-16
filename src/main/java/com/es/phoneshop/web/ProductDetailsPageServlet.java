package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.exceptions.QuantityMoreThanStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.HolderRecentProducts;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init() {
        arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    // TODO in case of error forward must occur
    // TODO /products/333 status code must be 404 instead of 500
    // TODO /products/qq status code must be 400 instead of 500
    // TODO zero or negative quantity must not be permitted
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        Product product = arrayListProductDao.getProduct(getIdFromURI(request));
        // TODO why do you create HttpSessionCartService for every request?
        HttpSessionCartService httpSessionCartService = new HttpSessionCartService(request);
        String objectQuantity = request.getParameter("quantity");
        String path = request.getContextPath() + "/products/" + request.getParameter("id");
        if (objectQuantity != null) {
            try {
                int quantity = Integer.parseInt(objectQuantity);
                if(httpSessionCartService.add(product.getId(), quantity + httpSessionCartService.getCartItemQuantityById(product.getId()))) {
                    path = request.getContextPath() + "/products/" + request.getParameter("id") + "?orderResult=" + "Product was added" + "&quantity=" + quantity;
                }else{
                    path = request.getContextPath() + "/products/" + request.getParameter("id") + "?orderResult=" + "Not enough stock" + "&quantity=" + quantity;
                }
                } catch (NumberFormatException ex) {
                path = request.getContextPath() + "/products/" + request.getParameter("id") + "?orderResult=" + "Insert the number";
            }
        }
        response.sendRedirect(path);
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        Product product = arrayListProductDao.getProduct(getIdFromURI(request));
        request.setAttribute("message", request.getParameter("orderResult"));
        request.setAttribute("product", product);
        HttpSession session = request.getSession();
        Object object = session.getAttribute("recentProducts");
        if (object != null) {
            HolderRecentProducts holderRecentProducts = new HolderRecentProducts((ArrayList<Product>) object);
            holderRecentProducts.updateList(product);
            session.setAttribute("recentProducts", holderRecentProducts.getRecentProducts());
        } else {
            List<Product> productArrayList = new ArrayList<>();
            productArrayList.add(product);
            session.setAttribute("recentProducts", productArrayList);
        }
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    private Long getIdFromURI(final HttpServletRequest request) {
        String[] massStr = request.getRequestURI().split("/");
        String string = massStr[massStr.length - 1];
        int index = string.indexOf("?");
        if (index != -1) {
            string = string.substring(0, index);
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException ex) {
            // TODO ProductNotFoundException is not a result of NumberFormatException
            throw new ProductNotFoundException("Product was not founded.");
        }
    }
}
