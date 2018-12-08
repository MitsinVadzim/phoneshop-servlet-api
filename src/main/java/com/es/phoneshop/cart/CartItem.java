package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

public class CartItem {

    private int quantity;
    private Product product;

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Id: " + product.getId() + "Description: " + product.getDescription() + "Stock: " + product.getStock() + "Quantity: " + quantity;
    }
}
