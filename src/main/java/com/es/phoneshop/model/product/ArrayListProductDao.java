package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class ArrayListProductDao implements ProductDao {

    //private static volatile ArrayListProductDao instance;

    private static ArrayListProductDao instance = null;

    private List<Product> productList = new ArrayList<>();

    private Long maxId = -1L;

    private ArrayListProductDao() {
    }

//    private static class ArrayListProductDaoHolder{
//        private final static ArrayListProductDao instance = new ArrayListProductDao();
//    }

    public synchronized static ArrayListProductDao getInstance(){
        if(instance == null)
            instance = new ArrayListProductDao();
        return instance;
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

    @Override
    public void deleteAll(){
        productList.clear();
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
