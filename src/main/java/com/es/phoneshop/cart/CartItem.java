package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.QuantityMoreThanStockException;
import com.es.phoneshop.model.product.Product;

public class CartItem {
    private int quantity;
    private Product product;

    // TODO don't check quantity here
    void setQuantity(int quantity) {
        int result = this.quantity + quantity;
        if (result > product.getStock()) {
            throw new QuantityMoreThanStockException("");
        } else {
            this.quantity = result;
        }
    }

    int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }


    CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Id: " + product.getId() + "Description: " + product.getDescription() + "Stock: " + product.getStock() + "Quantity: " + quantity;
    }
}
