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
        arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    // TODO why is this method the same as #doGet?
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        setCustomAttributes(request);
        // TODO when product successfully added to cart, you must do redirect
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }


    private void setCustomAttributes(final HttpServletRequest request) {
        // TODO avoid naming variables with prefixes like 'my'
        Product myProduct = arrayListProductDao.getProduct(getIdFromURI(request));
        HttpSession session = request.getSession();
        // TODO session should store 'Cart' instead of 'CartService'
        // TODO prefer interface type to class type
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
        // TODO why do you check 'quantity' param for GET method?
        if (request.getParameter("quantity") != null) {
            try {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (httpSessionCartService == null) {
                    httpSessionCartService = new HttpSessionCartService();
                }
                httpSessionCartService.add(myProduct.getId(), quantity);
                session.setAttribute("cart", httpSessionCartService);
                // TODO read task requirements carefully, success message should be passed via HTTP parameter
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
    // TODO why is this method the same as #doPost?
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private Long getIdFromURI(final HttpServletRequest request) {
        String[] massStr = request.getRequestURI().split("/");
        try {
            return Long.parseLong(massStr[massStr.length - 1]);
            // TODO don't handle NumberFormatException, map it to 400 error page in web.xml
        } catch (NumberFormatException ex) {
            throw new ProductNotFoundException("Product was not founded.");
        }

    }
}
