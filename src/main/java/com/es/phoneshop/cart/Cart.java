package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.QuantityMoreThanStockException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Cart {
    private List<CartItem> cartItemList = new ArrayList<>();

    void addToCart(CartItem cartItem) {
        if (cartItem.getQuantity() > cartItem.getProduct().getStock()) {
            throw new QuantityMoreThanStockException("");
        }
        int index = getIndexCartItemById(cartItem.getProduct().getId());
        if (index >= 0) {
            cartItemList.get(index).setQuantity(cartItem.getQuantity());
        } else {
            cartItemList.add(cartItem);
        }
    }

    private int getIndexCartItemById(Long id) {
        return IntStream.range(0, cartItemList.size())
                .filter(i -> id.equals(cartItemList.get(i).getProduct().getId())).findFirst().orElse(-1);
    }

    List<CartItem> getCartItemList() {
        return cartItemList;
    }
}
