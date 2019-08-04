package com.fpuente.ripley_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fpuente.ripley_cart.utils.SingletoneRipley;

public class FinishSale extends AppCompatActivity {

    ImageView exitCart;
    SingletoneRipley singletoneRipley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_sale);
        singletoneRipley = SingletoneRipley.getInstance(this);
        singletoneRipley.products.clear();
        ActivityCompat.invalidateOptionsMenu(this);
        exitCart = findViewById(R.id.exit_cart);
        exitCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartActivity = new Intent(FinishSale.this, MainActivity.class);
                startActivity(cartActivity);
                finish();
            }
        });
    }
}
