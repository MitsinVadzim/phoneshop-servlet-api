package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ArrayListProductDao implements ProductDao {


    private static ArrayListProductDao instance;

    private List<Product> productList = new ArrayList<>();

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
    public synchronized Product getProduct(final Long id) {
        int i = findPositionById(id);
        if (i == -1) {
            throw new RuntimeException("Product with " + id + " was not founded.");
        }
        return productList.get(findPositionById(id));
    }


    @Override
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
            product.setId(incMaxId());
            productList.add(product);
        } else {
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

    private synchronized Long incMaxId() {
        return ++identifier;
    }

    private synchronized void setMaxId(final Long id) {
        identifier = id;
    }


    private Long getMaxId() {
        return identifier;
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
