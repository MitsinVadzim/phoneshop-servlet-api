package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;

public class HolderRecentProducts {
    private List<Product> recentProducts = new ArrayList<>();

    public void updateList(Product product) {
        if (recentProducts.size() < 3) {
            recentProducts.add(0, product);
        } else {
            for (int i = recentProducts.size() - 1; i >= 1; i--) {
                recentProducts.set(i, recentProducts.get(i - 1));
            }
            recentProducts.set(0, product);
        }
    }

    public List<Product> getRecentProducts() {
        return recentProducts;
    }
}
