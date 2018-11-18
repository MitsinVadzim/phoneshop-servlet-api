package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts(String search,String sort);

    void save(Product product);

    void delete(Long id);

    void deleteAll();
}
