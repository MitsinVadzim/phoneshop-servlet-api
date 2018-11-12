package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class ArrayListProductDao implements ProductDao {

    private ArrayList<Product> productList;

    private Long maxId;

    public ArrayListProductDao() {
        productList = new ArrayList<>();
        maxId = 0L;
    }

    @Override
    public Product getProduct(Long id) {
        int i = findProductById(id);
        if(i != -1)
            return productList.get(i);
        return null;
    }

    @Override
    public List<Product> findProducts() {
        return productList;
    }

    @Override
    public void save(Product product) {
        if(product == null) throw new NullPointerException("product must not be null");
        product.setId(incId());
        productList.add(product);
    }

    @Override
    public void delete(Long id) {

        productList.remove(toIntExact(id-1));
    }

    @Override
    public void changeById(Long id, Product product){
        product.setId(id);
        int i = findProductById(id);
        if (i != -1 )
            productList.set(i, product);

    }

    private Long incId(){
        return maxId++;
    }

    private int findProductById(Long id){
        for (int i = 0; i < productList.size();i++){
            if(productList.get(i).getId()==id)
                return i;
        }
        return -1;
    }
}
