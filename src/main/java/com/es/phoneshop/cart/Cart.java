package com.es.phoneshop.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> cartItemList = new ArrayList<>();
    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addToCart(CartItem cartItem) {
        cartItemList.add(cartItem);
    }

    public void updateCartItemQuantity(int index, int quantity) {
        cartItemList.get(index).setQuantity(quantity);
    }

    public void deleteByIndex(int index){
        cartItemList.remove(index);
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
