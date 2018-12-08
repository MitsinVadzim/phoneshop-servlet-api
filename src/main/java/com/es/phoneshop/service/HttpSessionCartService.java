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
    private HttpServletRequest request;

    public HttpSessionCartService(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void add(Long productId, int quantity) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (ArrayListProductDao.getInstance().getProduct(productId).getStock()< quantity){
            throw new QuantityMoreThanStockException();
        }
        if (cart != null){
            int index = getIndexCartItemById(productId, cart.getCartItemList());
            if (index >= 0) {
                if (cart.getCartItemList().get(index).getQuantity() + quantity
                        > cart.getCartItemList().get(index).getProduct().getStock()) {
                    throw new QuantityMoreThanStockException();
                } else {
                    cart.updateCartItemQuantity(index, quantity + cart.getCartItemList().get(index).getQuantity());
                }
            } else {
                cart.addToCart(new CartItem(quantity, ArrayListProductDao.getInstance().getProduct(productId)));
            }
        }else {
            cart = new Cart();
            cart.addToCart(new CartItem(quantity, ArrayListProductDao.getInstance().getProduct(productId)));
        }
        request.getSession().setAttribute("cart",cart);
    }

    private int getIndexCartItemById(Long id, List<CartItem> cartItemList) {
        return IntStream.range(0, cartItemList.size())
                .filter(i -> id.equals(cartItemList.get(i).getProduct().getId())).findFirst().orElse(-1);
    }
}
