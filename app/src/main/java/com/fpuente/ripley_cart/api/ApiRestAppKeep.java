package com.fpuente.ripley_cart.api;


import com.fpuente.ripley_cart.config.Config;

import org.json.JSONObject;

import java.util.HashMap;


public class ApiRestAppKeep {


    public AsyncTaskResult<JSONObject> getProducts(String  product){
        try {
            JSONObject input = new JSONObject();
            input.put("url", Config.base_url_products +product);
            input.put("method", "GET");

            return new Apicaller().execute(input).get();
        }catch (Exception e){
            return new  AsyncTaskResult<>(e);
        }
    }

    public AsyncTaskResult<JSONObject> createCart(String customer_id){
        try {
            HashMap<String,String> hs = new HashMap<>();
            hs.put("_method","POST");
            hs.put("customer_id",customer_id);
            JSONObject input = new JSONObject();
            input.put("url", Config.cart);
            input.put("method", "POST");
            input.put("body",new JSONObject(hs));
            return new Apicaller().execute(input).get();
        }catch (Exception e){
            return new  AsyncTaskResult<>(e);
        }
    }



}
