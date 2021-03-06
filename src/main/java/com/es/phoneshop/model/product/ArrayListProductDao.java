package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ArrayListProductDao implements ProductDao {


    private static ArrayListProductDao instance;

    // TODO make this field final
    private final List<Product> productList = new ArrayList<>();

    // TODO ids should be started with 1
    private Long identifier = -1L;

    private ArrayListProductDao() {

    }


    public synchronized static ArrayListProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    @Override
    // TODO use Stream API code convention
    public synchronized Product getProduct(final Long id) {
        return productList.stream().filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with" + id + "was not founded."));
    }


    @Override
    // TODO use Stream API code convention
    public List<Product> findProducts() {

        return productList.stream().filter(x -> x.getPrice() != null)
                .filter(x -> x.getStock() > 0).collect(Collectors.toList());

    }

    @Override
    public synchronized void save(final Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product must not be null");
        }
        if (product.getId() == null) {
            product.setId(++identifier);
            productList.add(product);
        } else {
            // TODO throw exception if the product is not present. Do it something like this:
            // Product target = getProduct(product.getId());
            // update(target, product); // update would be a private method that just set product properties
            int i = findPositionById(product.getId());
            if (i == -1) {
                productList.add(product);
            } else {
                productList.set(i, product);
            }
        }
    }

    @Override
    public synchronized void delete(final Long id) {
        productList.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public synchronized void deleteAll() {
        productList.clear();
    }


    private int findPositionById(final Long id) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
