package com.es.phoneshop.exceptions;

public class QuantityMoreThanStockException extends RuntimeException {
    public QuantityMoreThanStockException(String message) {
        super(message);
    }
}
