// TODO move to com.es.phoneshop.service
package com.es.phoneshop.logic;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Priority;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// TODO rename to ProductService
public final class ProductDaoLogic {

    private static ProductDaoLogic instance;

    // TODO final
    private ProductDao productList = ArrayListProductDao.getInstance();


    private ProductDaoLogic() {

    }

    public synchronized static ProductDaoLogic getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ProductDaoLogic();
                }
            }
        }
        return instance;
    }

    private List<Product> searchProduct(final List<Product> list, final String search) {
        // TODO use Stream API
        List<Priority> priorityList = new ArrayList<>();
        for (Product aProductList : list) {
            priorityList.add(new Priority(aProductList));
        }

        // TODO use Map<Product, Integer> instead of Priority
        for (String retval : search.split("\\s")) {
            for (Priority aPriorityList : priorityList) {
                if (aPriorityList.getProduct().getDescription().contains(retval)) {
                    aPriorityList.incPriority();
                }
            }

        }

        priorityList = priorityList.stream()
                .filter(x -> x
                        .getPriority() > 0)
                .sorted(Comparator
                        .comparing(Priority::getPriority)
                        .reversed()/*TODO thenComparing*/)
                // TODO .map(Priority::getProduct)
                .collect(Collectors.toList());
        List<Product> result = new ArrayList<>();
        for (Priority product : priorityList) {
            result.add(product.getProduct());
        }
        return result;
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
