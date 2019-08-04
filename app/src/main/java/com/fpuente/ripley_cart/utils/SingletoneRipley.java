package com.fpuente.ripley_cart.utils;

import android.content.Context;

import com.fpuente.ripley_cart.model.Product;

import java.util.ArrayList;


public class SingletoneRipley {


    private static SingletoneRipley instance;
    Context context;
    public ArrayList <Product> products = new ArrayList<>();
    public Product actualProduct;


    public SingletoneRipley(Context context)
    {
        this.context = context;

    }

    public synchronized static SingletoneRipley getInstance (Context context)
    {
        if(instance == null)
        {
            instance = new SingletoneRipley(context);
        }
        return instance;
    }
    public int totalProductsInCart(){
        int total = 0;
        for (Product product: products) {
            total+= product.getQuantity();
        }
        return total;

    }
    public int totalPriceCart(){
        int total = 0;
        for (Product product: products) {
            if(product.getCardPrice() > 0){
                total+= product.getCardPrice()*product.getQuantity();
            }else{
                total+= product.getNormalPrice()*product.getQuantity();
            }

        }
        return total;

    }
    public int totalPriceNormal(){
        int total = 0;
        for (Product product: products) {
            total+= product.getNormalPrice()*product.getQuantity();

        }
        return total;

    }


}
