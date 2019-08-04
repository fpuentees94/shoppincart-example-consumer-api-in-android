package com.fpuente.ripley_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextScene(MainActivity.class);
            }
        });
    }
    public  void  nextScene(final Class activity)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                setScene(activity);
            }
        }, 2000);
    }
    public  void setScene(Class activity)
    {
        Intent intent = new Intent();

        intent.setClass(this, activity);
        startActivity(intent);

        overridePendingTransition(
                R.anim.fadein,
                R.anim.fadeout
        );
    }
}
