package com.es.phoneshop.web;

import com.es.phoneshop.interfaces.ICartService;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.HolderRecentProducts;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.review.Review;
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

    private ICartService httpSessionCartService;

    @Override
    public void init() {
        arrayListProductDao = ArrayListProductDao.getInstance();
        httpSessionCartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Product product = arrayListProductDao.getElement(getIdFromURI(request));
        //Дальше идёт очень убогий код, в идеале это нужно было бы вынести в сервис. Но на момент создания этого сервлета я не создал сервис к сожалению(
        String nameUser = request.getParameter("name");
        String comment = request.getParameter("comment");
        if (!(nameUser ==null || comment == null)){
            int rating = Integer.parseInt(request.getParameter("rating"));
            double recalculatedRating = (product.getAverageRating()*product.getReviewList().size() + rating)/(product.getReviewList().size()+1);
            product.setAverageRating(recalculatedRating);
            product.getReviewList().add(new Review(comment, nameUser, rating));
        }
        HttpSession session = request.getSession();
        String objectQuantity = request.getParameter("quantity");
        String path = request.getContextPath() + "/products/" + request.getParameter("id");
        if (objectQuantity != null) {
            try {
                int quantity = Integer.parseInt(objectQuantity);
                if (quantity > 0) {
                    if (httpSessionCartService.add(product.getId(), quantity + httpSessionCartService.getCartItemQuantityById(product.getId(), session), session)) {
                        path = request.getContextPath() + "/products/" + request.getParameter("id") + "?orderResult=" + "Product was added" + "&quantity=" + quantity;
                    } else {
                        path = request.getContextPath() + "/products/" + request.getParameter("id") + "?orderResult=" + "Not enough stock" + "&quantity=" + quantity;
                    }
                }else{
                    path = request.getContextPath() + "/products/" + request.getParameter("id") + "?orderResult=" + "Incorrect number" + "&quantity=" + quantity;
                }
            } catch (NumberFormatException ex) {
                path = request.getContextPath() + "/products/" + request.getParameter("id") + "?orderResult=" + "Insert the number";
            }
        }
        response.sendRedirect(path);
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        Product product = arrayListProductDao.getElement(getIdFromURI(request));
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
        return Long.parseLong(string);
    }
}
