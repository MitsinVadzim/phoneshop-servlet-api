package com.es.phoneshop.exceptions;

// TODO don't use exceptions for flow controlling
public class QuantityMoreThanStockException extends RuntimeException {
    public QuantityMoreThanStockException(String message) {
        super(message);
    }
}
