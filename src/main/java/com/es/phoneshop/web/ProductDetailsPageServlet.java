package com.es.phoneshop.web;

import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.exceptions.QuantityMoreThanStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.HolderRecentProducts;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init() {
        // TODO what's the point of calling super.init() ?
        arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        // TODO what will happen if id is not a valid long value?
        setCustomAttributes(request);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }


    private void setCustomAttributes(final HttpServletRequest request) {
        Product myProduct = arrayListProductDao.getProduct(getIdFromURI(request));
        HttpSession session = request.getSession();
        HttpSessionCartService httpSessionCartService = (HttpSessionCartService) session.getAttribute("cart");
        HolderRecentProducts recentProducts = (HolderRecentProducts) session.getAttribute("recentProducts");
        if (recentProducts == null) {
            recentProducts = new HolderRecentProducts();
            recentProducts.updateList(myProduct);
        } else {
            if (!recentProducts.getRecentProducts().get(0).getId().equals(myProduct.getId())) {
                recentProducts.updateList(myProduct);
            }
        }
        if (request.getParameter("quantity") != null) {
            try {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (httpSessionCartService == null) {
                    httpSessionCartService = new HttpSessionCartService();
                }
                httpSessionCartService.add(myProduct.getId(), quantity);
                session.setAttribute("cart", httpSessionCartService);
                request.setAttribute("message", "Successfully added to cart");
            } catch (NumberFormatException ex) {
                request.setAttribute("error", "Insert the number");
            } catch (QuantityMoreThanStockException ex) {
                request.setAttribute("error", "Not enough stock");
            }
        }
        session.setAttribute("recentProducts", recentProducts);
        request.setAttribute("cart", httpSessionCartService);
        request.setAttribute("product", myProduct);
        request.setAttribute("recentProducts", recentProducts.getRecentProducts());
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private Long getIdFromURI(final HttpServletRequest request) {
        String[] massStr = request.getRequestURI().split("/");
        try {
            return Long.parseLong(massStr[massStr.length - 1]);
        } catch (NumberFormatException ex) {
            throw new ProductNotFoundException("Product was not founded.");
        }

    }
}
