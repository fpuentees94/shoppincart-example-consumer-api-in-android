package com.fpuente.ripley_cart.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceCart {
    private final SharedPreferences mPrefs;

    private String KEY_CART_ID = "KEY_CUSTOMER_ID";



    public PreferenceCart(Context context) {

        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getKeyCartId() {
        return mPrefs.getString(KEY_CART_ID,"");
    }

    public void setKeyCartId(String key_cart_id) {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(KEY_CART_ID, key_cart_id);
        mEditor.apply();
    }




}
