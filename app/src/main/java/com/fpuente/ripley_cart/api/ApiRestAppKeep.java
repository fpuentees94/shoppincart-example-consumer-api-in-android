package com.fpuente.ripley_cart.api;


import com.fpuente.ripley_cart.config.Config;
import com.fpuente.ripley_cart.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ApiRestAppKeep {

    private Config config = new Config();
    private String TAG = "Config";



    public AsyncTaskResult<JSONObject> getProducts(String  product){
        try {
            JSONObject input = new JSONObject();
            input.put("url", config.base_url_products+product);
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
            input.put("url", config.cart);
            input.put("method", "POST");
            input.put("body",new JSONObject(hs));
            return new Apicaller().execute(input).get();
        }catch (Exception e){
            return new  AsyncTaskResult<>(e);
        }
    }
    public AsyncTaskResult<JSONObject> addProduct(String customer_id, String cart_id, String sku, int price, int quantity){
        try {
            /*Map<String,Object> productJson = new HashMap<>();
            productJson.put("price", String.valueOf(price));
            productJson.put("sku",sku);
            productJson.put("quantity", String.valueOf(quantity));*/

            JSONObject productJson = new JSONObject();
            productJson.put("price", String.valueOf(price));
            productJson.put("sku",sku);
            productJson.put("quantity", String.valueOf(quantity));

            JSONArray products  = new JSONArray();
            products.put(productJson);



            HashMap<String,String> hs = new HashMap<>();
            hs.put("_method","POST");
            hs.put("customer_id",customer_id);
            hs.put("cart_id",cart_id);
            hs.put("products", String.valueOf(products));



            JSONObject input = new JSONObject();
            input.put("url", config.products);
            input.put("method", "POST");
            input.put("body",new JSONObject(hs));
            return new Apicaller().execute(input).get();
        }catch (Exception e){
            return new  AsyncTaskResult<>(e);
        }
    }


}
