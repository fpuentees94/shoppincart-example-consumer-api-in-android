package com.fpuente.ripley_cart.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Product  implements Serializable {

    String name;
    String thumbnailImage;
    ArrayList<String> images;
    int normalPrice = 0;
    int cardPrice = 0;
    String SKU;
    ArrayList<Attribute> Attributes;
    int quantity;


    public Product() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

    public int getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(int cardPrice) {
        this.cardPrice = cardPrice;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<Attribute> getAttributes() {
        return Attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        Attributes = attributes;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
