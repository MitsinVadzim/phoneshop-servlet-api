package com.es.phoneshop.cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private List<CartItem> cartItemList = new ArrayList<>();

    public void addToCart(CartItem cartItem) {
        cartItemList.add(cartItem);
    }

    public void updateCartItemQuantity(int index, int quantity) {
        cartItemList.get(index).setQuantity(quantity);
    }

    public List<CartItem> getCartItemList() {
        return Collections.unmodifiableList(cartItemList);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (CartItem aCartItemList : cartItemList) {
            text.append("Id: ").append(aCartItemList.getProduct().getId())
                    .append("Code: ").append(aCartItemList.getProduct().getCode())
                    .append("Quantity: ").append(aCartItemList.getQuantity())
                    .append("Stock: ").append(aCartItemList.getProduct().getStock());
        }
        return text.toString();
    }
}
