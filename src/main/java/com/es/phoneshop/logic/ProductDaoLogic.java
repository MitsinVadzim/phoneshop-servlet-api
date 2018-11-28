package com.es.phoneshop.logic;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Priority;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.util.*;
import java.util.stream.Collectors;

public class ProductDaoLogic {

    private static ProductDaoLogic instance;

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

    private List<Product> searchProduct(List<Product> list,String search){
        List<Priority> priorityList = new ArrayList<>();
        for (Product aProductList : productList.findProducts()) {
            priorityList.add(new Priority(aProductList));
        }

        for (String retval : search.split("\\s")) {
            for (Priority aPriorityList : priorityList) {
                if (aPriorityList.getProduct().getDescription().contains(retval))
                    aPriorityList.incPriority();
            }

        }

        priorityList = priorityList.stream().filter(x->x.getPriority()>0).sorted(Comparator.comparing(Priority::getPriority).reversed()).collect(Collectors.toList());
        List<Product> result = new ArrayList<>();
        for (Priority product: priorityList) {
            result.add(product.getProduct());
        }
        return result;
    }

    public List<Product> findProducts(String search, String sort) {

        if (search.equals("")) {
            return productList.findProducts();
        }
        List<Product> result = searchProduct(productList.findProducts(),search);

        switch (sort) {
            case "ascDescription":
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
