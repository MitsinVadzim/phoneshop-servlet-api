package com.es.phoneshop.interfaces;

import javax.servlet.http.HttpSession;

public interface ICartService {
    boolean add(Long productId, int quantity, HttpSession session);
    int getCartItemQuantityById(Long productId, HttpSession session);
    void delete(Long productId, HttpSession session);
    void recalculateCart(HttpSession session);
}
