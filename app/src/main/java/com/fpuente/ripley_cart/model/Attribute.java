package com.fpuente.ripley_cart.model;

import java.io.Serializable;

public class Attribute implements Serializable {
    String name;
    String value;

    public Attribute(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
