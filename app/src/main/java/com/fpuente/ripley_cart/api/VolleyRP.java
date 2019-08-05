package com.fpuente.ripley_cart.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class VolleyRP {
    private static VolleyRP mVolleyRP = null;
    private RequestQueue mRequestQueue;

    private VolleyRP(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    static VolleyRP getInstance(Context context) {
        if (mVolleyRP == null) {
            mVolleyRP = new VolleyRP(context);
        }
        return mVolleyRP;
    }


    RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    static void addToQueue(Request request, RequestQueue fRequestQueue, Context context, VolleyRP volley) {
        if (request != null) {
            request.setTag(context);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            fRequestQueue.add(request);
        }
    }
}
