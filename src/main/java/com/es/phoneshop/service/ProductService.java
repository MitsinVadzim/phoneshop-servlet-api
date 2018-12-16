package com.es.phoneshop.service;

import com.es.phoneshop.interfaces.IDao;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ProductService {

    private static ProductService instance;

    private final IDao<Product, Long> productList = ArrayListProductDao.getInstance();


    private ProductService() {

    }

    public synchronized static ProductService getInstance() {
        if (instance == null) {
            synchronized (ProductService.class) {
                if (instance == null) {
                    instance = new ProductService();
                }
            }
        }
        return instance;
    }


    private List<Product> searchProduct(final List<Product> list, final String search) {

        Map<Product, Long> mapResult =
                list.stream().collect(Collectors.toMap(c -> c, l->0L));
        //mapResult = mapResult.entrySet().stream().peek(x -> x.setValue(0L)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (String retval : search.split("\\s")) {
            mapResult = mapResult.entrySet().stream().peek(x -> {

                if (x.getKey().getDescription().contains(retval)) {
                    x.setValue(x.getValue() + 1);
                }
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        List<Product> filtredList = mapResult.entrySet().stream().filter(x -> x.getValue() > 0)
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return filtredList;
    }

    public List<Product> findProducts(final String search, final String sort) {

        if (search.equals("")) {
            return productList.findElements();
        }
        List<Product> result = searchProduct(productList.findElements(), search);

        switch (sort) {
            case "ascDescription":
                // TODO use thenComparing in order to avoid extra traversing and sorting
                result.sort(Comparator.comparing(Product::getDescription));
                break;
            case "descDescription":
                result.sort(Comparator.comparing(Product::getDescription).reversed());
                break;
            case "ascPrice":
                result.sort(Comparator.comparing(Product::getPrice));
                break;
            case "descPrice":
                result.sort(Comparator.comparing(Product::getPrice).reversed());
                break;
        }


        return result;
    }
}
