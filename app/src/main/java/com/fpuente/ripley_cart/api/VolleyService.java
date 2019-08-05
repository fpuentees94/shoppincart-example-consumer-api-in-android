package com.fpuente.ripley_cart.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public abstract class VolleyService {


    public abstract void requestSuccessful(JSONObject j);
    public abstract void requestFailed();

    protected VolleyService(){}

    public void requestsJsonPOST(Context c, String URL, JSONObject h){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,h, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject data) {
                requestSuccessful(data);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                requestFailed();
            }
        });
        VolleyRP.addToQueue(request,VolleyRP.getInstance(c).getRequestQueue(),c,VolleyRP.getInstance(c));
    }

    public void requestJsonGET(Context c,String URL){
        JsonObjectRequest request = new JsonObjectRequest(URL,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject data) {
                requestSuccessful(data);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                requestFailed();
            }
        });
        VolleyRP.addToQueue(request,VolleyRP.getInstance(c).getRequestQueue(),c,VolleyRP.getInstance(c));
    }
    public void requestsJsonPUT(Context c, String URL, JSONObject h){
        Log.d("QUE TREASS",""+h.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,URL,h, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject data) {
                requestSuccessful(data);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                requestFailed();
            }
        });
        VolleyRP.addToQueue(request,VolleyRP.getInstance(c).getRequestQueue(),c,VolleyRP.getInstance(c));
    }


}
