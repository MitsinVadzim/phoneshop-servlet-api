package com.es.phoneshop.model.product;

import java.util.List;

public class HolderRecentProducts {
    private List<Product> recentProducts;
    private final int LIST_SIZE = 3;

    public HolderRecentProducts(List<Product> productList) {
        this.recentProducts = productList;
    }

    public void updateList(Product product) {
        recentProducts.removeIf(x -> x.getId().equals(product.getId()));
        recentProducts.add(0, product);
        if (recentProducts.size() > LIST_SIZE){
            recentProducts.remove(LIST_SIZE);
        }
    }

    public List<Product> getRecentProducts() {
        return recentProducts;
    }
}
