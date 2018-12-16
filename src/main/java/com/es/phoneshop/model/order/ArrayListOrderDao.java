package com.es.phoneshop.model.order;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.interfaces.IDao;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements IDao<Order, String> {

    private static ArrayListOrderDao instance;

    private final List<Order> orderList = new ArrayList<>();

    private ArrayListOrderDao() {

    }

    public synchronized static ArrayListOrderDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Order getElement(String id) {
        return orderList.stream().filter(x -> x.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException("Order with" + id + "was not founded."));
    }

    @Override
    public List<Order> findElements() {
        return orderList;
    }

    @Override
    public void save(Order element) {
        orderList.add(element);
    }

    @Override
    public void delete(String id) {
        orderList.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public void deleteAll() {
        orderList.clear();
    }
}
