package com.es.phoneshop.model.product;

public class Priority {
    private Product product;
    private int priority;

    public Priority(final Product fproduct) {
        this.product = fproduct;
        priority = 0;
    }

    public int getPriority() {
        return priority;
    }

    public void incPriority() {
        ++priority;
    }


    public Product getProduct() {
        return product;
    }
}
