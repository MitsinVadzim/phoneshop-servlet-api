package com.es.phoneshop.service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.QuantityMoreThanStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.IntStream;

public class HttpSessionCartService implements CartService {
    // TODO pass request (better session) via method parameter
    private HttpServletRequest request;

    public HttpSessionCartService(HttpServletRequest request) {
        this.request = request;
    }

    public int getCartItemQuantityById(Long productId) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            int index = getIndexCartItemById(productId, cart.getCartItemList());
            if (index >=0) {
                return cart.getCartItemList().get(index).getQuantity();
            }
        }
        return 0;
    }

    @Override
    public boolean add(Long productId, int quantity) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (ArrayListProductDao.getInstance().getProduct(productId).getStock() < quantity) {
            return false;
        }
        if (cart != null) {
            int index = getIndexCartItemById(productId, cart.getCartItemList());
            if (index >= 0) {
                cart.updateCartItemQuantity(index, quantity);
            } else {
                // TODO dao must be member variable
                cart.addToCart(new CartItem(quantity, ArrayListProductDao.getInstance().getProduct(productId)));
            }
        } else {
            cart = new Cart();
            // TODO dao must be member variable
            cart.addToCart(new CartItem(quantity, ArrayListProductDao.getInstance().getProduct(productId)));
        }
        request.getSession().setAttribute("cart", cart);
        return true;
    }

    public void delete(Long productId){
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            int index = getIndexCartItemById(productId, cart.getCartItemList());
            if (index >= 0) {
                cart.deleteByIndex(index);
                // TODO not necessary
                request.getSession().setAttribute("cart", cart);
            }
        }
    }

    private int getIndexCartItemById(Long id, List<CartItem> cartItemList) {
        return IntStream.range(0, cartItemList.size())
                .filter(i -> id.equals(cartItemList.get(i).getProduct().getId())).findFirst().orElse(-1);
    }
}
