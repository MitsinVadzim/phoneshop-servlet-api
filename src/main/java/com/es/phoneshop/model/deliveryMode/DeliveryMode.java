package com.es.phoneshop.model.deliveryMode;

public class DeliveryMode {
    String mode;
    int cost;
    Long id;

    public DeliveryMode(String mode, int cost, Long id) {
        this.mode = mode;
        this.cost = cost;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
