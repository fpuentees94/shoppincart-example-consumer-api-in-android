package com.fpuente.ripley_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpuente.ripley_cart.component.CartAdapter;
import com.fpuente.ripley_cart.utils.SingletoneRipley;

public class Cart extends AppCompatActivity {

    RelativeLayout emptyCart, fullCart;
    SingletoneRipley singletoneRipley;
    TextView txtQuantity, txtPriceList, txtPriceCart;
    ImageView exitCart;
    Button saleOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        singletoneRipley = SingletoneRipley.getInstance(this);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        saleOrder = findViewById(R.id.sale_order);

        CartAdapter mAdapter = new CartAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addItems(singletoneRipley.products);

        emptyCart = findViewById(R.id.empty_cart);
        fullCart  = findViewById(R.id.full_cart);
        if(singletoneRipley.products.size() > 0){
            emptyCart.setVisibility(View.GONE);
            fullCart.setVisibility(View.VISIBLE);
        }else{
            fullCart.setVisibility(View.GONE);
            emptyCart.setVisibility(View.VISIBLE);
        }

        txtQuantity = findViewById(R.id.txt_quantity);
        txtPriceList = findViewById(R.id.txt_list_price);
        txtPriceCart = findViewById(R.id.txt_card_price);

        int quantity = singletoneRipley.totalProductsInCart();
        if(quantity > 1){
            txtQuantity.setText(quantity +"  productos");
        }else{
            txtQuantity.setText(quantity +"  producto");
        }
        txtPriceCart.setText("$"+singletoneRipley.totalPriceCart());
        txtPriceList.setText("$"+singletoneRipley.totalPriceNormal());
        exitCart = findViewById(R.id.exit_cart);
        exitCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        saleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartActivity = new Intent(Cart.this, FinishSale.class);
                startActivity(cartActivity);
                finish();
            }
        });




    }
}
