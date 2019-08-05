package com.fpuente.ripley_cart.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceCart {
    private final SharedPreferences mPrefs;

    private String KEY_CART_ID = "KEY_CART_ID";
    private String KEY_CUSTOMER_ID = "KEY_CUSTOMER_ID";
    private String KEY_TOTAL_PRICE = "KEY_TOTAL__PRICE";



    public PreferenceCart(Context context) {

        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getKeyCartId() {
        return mPrefs.getString(KEY_CART_ID,"");
    }
    public String getKeyCustomerId() {
        return mPrefs.getString(KEY_CUSTOMER_ID,"");
    }
    public Integer getKeyTotalPrice() {
        return mPrefs.getInt(KEY_TOTAL_PRICE,0);
    }

    public void setKeyCartId(String key_cart_id) {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(KEY_CART_ID, key_cart_id);
        mEditor.apply();
    }

    public void setKeyCustomerId(String key_customer_id) {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(KEY_CUSTOMER_ID, key_customer_id);
        mEditor.apply();
    }

    public void setKeyTotalPrice(Integer key_total_price) {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt(KEY_TOTAL_PRICE, key_total_price);
        mEditor.apply();
    }




}
