// TODO move to com.es.phoneshop.service
package com.es.phoneshop.service;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO rename to ProductService
public final class ProductService {

    private static ProductService instance;

    // TODO final
    private final ProductDao productList = ArrayListProductDao.getInstance();


    private ProductService() {

    }

    public synchronized static ProductService getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ProductService();
                }
            }
        }
        return instance;
    }


    private List<Product> searchProduct(final List<Product> list, final String search) {

        Map<Product, Long> mapResult =
                list.stream().collect(Collectors.toMap(c -> c, Product::getId));
        mapResult = mapResult.entrySet().stream().peek(x -> x.setValue(0L)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // TODO use Map<Product, Integer> instead of Priority
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
            return productList.findProducts();
        }
        List<Product> result = searchProduct(productList.findProducts(), search);

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
