package com.es.phoneshop.model.deliveryMode;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.interfaces.IDao;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDeliveryModeDao implements IDao<DeliveryMode, Long> {
    private static ArrayListDeliveryModeDao instance;

    private final List<DeliveryMode> modeList = new ArrayList<>();

    private ArrayListDeliveryModeDao() {
    }

    public synchronized static ArrayListDeliveryModeDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListDeliveryModeDao.class) {
                if (instance == null) {
                    instance = new ArrayListDeliveryModeDao();
                }
            }
        }
        return instance;
    }

    @Override
    public DeliveryMode getElement(Long id) {
        return modeList.stream().filter(x -> x.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Mode not exist"));
    }

    @Override
    public List<DeliveryMode> findElements() {
        return modeList;
    }

    @Override
    public void save(DeliveryMode element) {
        modeList.add(element);
    }

    @Override
    public void delete(Long id) {
        modeList.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public void deleteAll() {
        modeList.clear();
    }
}
