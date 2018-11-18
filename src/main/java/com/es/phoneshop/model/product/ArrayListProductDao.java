package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {


    private static ArrayListProductDao instance = null;

    private List<Product> productList = new ArrayList<>();

    private Long maxId = -1L;

    private ArrayListProductDao() {
        Currency usd = Currency.getInstance("USD");
        productList.add(new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productList.add(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productList.add(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productList.add(new Product(3L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productList.add(new Product(4L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productList.add(new Product(5L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productList.add(new Product(6L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productList.add(new Product(7L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productList.add(new Product(8L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        productList.add(new Product(9L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        productList.add(new Product(10L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        productList.add(new Product(11L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        productList.add(new Product(12L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));

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
        switch (sort){
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
