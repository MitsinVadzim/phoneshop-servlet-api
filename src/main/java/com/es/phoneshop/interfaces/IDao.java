package com.es.phoneshop.interfaces;

import java.util.List;

public interface IDao<T, U> {
    T getElement(U id);

    List<T> findElements();

    void save(T element);

    void delete(U id);

    void deleteAll();
}
