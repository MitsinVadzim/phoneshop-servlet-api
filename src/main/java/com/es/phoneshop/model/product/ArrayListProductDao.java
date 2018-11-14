package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class ArrayListProductDao implements ProductDao {

    private ArrayList<Product> productList;

    private Long maxId;

    public ArrayListProductDao() {
        productList = new ArrayList<>();
        maxId = -1L;
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
        if(product == null) throw new IllegalArgumentException("product must not be null");
        if(product.getId().equals(-1L)){
            product.setId(incMaxId());
            productList.add(product);
        }else{
            int i = findProductById(product.getId());
            if(i!=-1){
                productList.set(i,product);
            }else {
                productList.add(product);
                setMaxId(product.getId());
            }

        }
    }

    @Override
    public void delete(Long id) {
        int i = findProductById(id);
        if(i != -1) {
            productList.remove(i);
            decMaxId();
        }
    }


    private Long incMaxId(){
        return ++maxId;
    }

    private void setMaxId(Long id){
        maxId = id;
    }

    private void decMaxId(){
        --maxId;
    }

    private Long getMaxId(){
        return maxId;
    }

    private int findProductById(Long id){
        for (int i = 0; i < productList.size();i++){
            if(productList.get(i).getId()==id)
                return i;
        }
        return -1;
    }
}
