package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;

public class HttpSessionCartService implements CartService {
    private Cart cartSession = new Cart();


    @Override
    public void add(Long productId, int quantity) {
        cartSession.addToCart(new CartItem(quantity, ArrayListProductDao.getInstance().getProduct(productId)));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (CartItem cartItem : cartSession.getCartItemList()) {
            result.append("Code: ").append(cartItem.getProduct().getCode()).append("Quantity: ").append(cartItem.getQuantity());
        }
        return result.toString();
    }
}
