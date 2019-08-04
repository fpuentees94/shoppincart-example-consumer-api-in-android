package com.fpuente.ripley_cart.model;

public class Category {
    String Name;
    String SKU;

    public Category() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }
}
