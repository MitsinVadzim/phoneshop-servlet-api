package com.es.phoneshop.mapping;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ArrayListMapping {
    private static ArrayListMapping instance;

    private final List<MinicartLink> mappingList = new ArrayList<>();

    private ArrayListMapping() {
    }

    public synchronized static ArrayListMapping getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListMapping();
                }
            }
        }
        return instance;
    }

    public List<MinicartLink> getMappingList() {
        return mappingList;
    }

    public void add(String nameLink, String link){
        mappingList.add(new MinicartLink(nameLink, link));
    }
}
