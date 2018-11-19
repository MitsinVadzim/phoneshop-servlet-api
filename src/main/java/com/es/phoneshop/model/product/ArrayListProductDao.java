package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;

public class ArrayListProductDao implements ProductDao {


    private static ArrayListProductDao instance = null;

    private List<Product> productList = new ArrayList<>();

    private Long maxId = -1L;

    private ArrayListProductDao() {

    }


    public synchronized static ArrayListProductDao getInstance() {
        if (instance == null)
            instance = new ArrayListProductDao();
        return instance;
    }

    @Override
    public Product getProduct(Long id) {
        int i = findProductById(id);
        if (i != -1)
            return productList.get(i);
        return null;
    }


    @Override
    public List<Product> findProducts(String search, String sort) {

        if (search.equals("")) {
            return productList;
        }
        List<Priority> priorityList = new ArrayList<>();
        for (Product aProductList : productList) {
            priorityList.add(new Priority(aProductList));
        }

        for (String retval : search.split(" ")) {
            for (Priority aPriorityList : priorityList) {
                if (aPriorityList.getProduct().getDescription().contains(retval))
                    aPriorityList.incPriority();
            }
        }

        priorityList.sort(Comparator.comparing(Priority::getPriority));
        Collections.reverse(priorityList);
        List<Product> result = new ArrayList<>();
        int i = 0;
        while (i < priorityList.size() && priorityList.get(i).getPriority() != 0) {
            result.add(priorityList.get(i).getProduct());
            ++i;
        }
        switch (sort) {
            case "ascDescription":
                result.sort(Comparator.comparing(Product::getDescription));
                break;
            case "descDescription":
                result.sort(Comparator.comparing(Product::getDescription));
                Collections.reverse(result);
                break;
            case "ascPrice":
                result.sort(Comparator.comparing(Product::getPrice));
                break;
            case "descPrice":
                result.sort(Comparator.comparing(Product::getPrice));
                Collections.reverse(result);
                break;
        }


        return result;
    }

    @Override
    public synchronized void save(Product product) {
        if (product == null) throw new IllegalArgumentException("product must not be null");
        if (product.getId().equals(-1L)) {
            product.setId(incMaxId());
            productList.add(product);
        } else {
            int i = findProductById(product.getId());
            if (i != -1) {
                productList.set(i, product);
            } else {
                productList.add(product);
                setMaxId(product.getId());
            }

        }
    }

    @Override
    public synchronized void delete(Long id) {
        int i = findProductById(id);
        if (i != -1) {
            productList.remove(i);
            decMaxId();
        }
    }

    @Override
    public synchronized void deleteAll() {
        productList.clear();
    }

    private synchronized Long incMaxId() {
        return ++maxId;
    }

    private synchronized void setMaxId(Long id) {
        maxId = id;
    }

    private synchronized void decMaxId() {
        --maxId;
    }

    private Long getMaxId() {
        return maxId;
    }

    private int findProductById(Long id) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }
}
