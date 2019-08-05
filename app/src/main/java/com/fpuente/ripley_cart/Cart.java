package com.fpuente.ripley_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.fpuente.ripley_cart.api.VolleyService;
import com.fpuente.ripley_cart.component.CartAdapter;
import com.fpuente.ripley_cart.config.Config;
import com.fpuente.ripley_cart.model.Product;
import com.fpuente.ripley_cart.preferences.PreferenceCart;
import com.fpuente.ripley_cart.utils.ServiceUtils;
import com.fpuente.ripley_cart.utils.SingletoneRipley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart extends AppCompatActivity {

    RelativeLayout emptyCart, fullCart;
    SingletoneRipley singletoneRipley;
    TextView txtQuantity, txtPriceList, txtPriceCart;
    ImageView exitCart;
    Button saleOrder;
    PreferenceCart preferenceCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        singletoneRipley = SingletoneRipley.getInstance(this);
        preferenceCart = new PreferenceCart(this);
        getProductToCart(preferenceCart.getKeyCustomerId(),preferenceCart.getKeyCartId());

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
    public void getProductToCart( String customer_id, String cart_id){
        VolleyService request = new VolleyService() {
            @Override
            public void requestSuccessful(JSONObject j) {
                Log.d("requesttttttttt",j.toString());
                try {
                    JSONArray productsJSON = j.getJSONArray("products");
                    ArrayList<Product> products = new ArrayList<>();
                    for(int i = 0 ; i < productsJSON.length(); i ++){

                        JSONObject productObject = productsJSON.getJSONObject(i);
                        Product product = new Product();
                        product.setSKU(productObject.getString("sku"));
                        products.add(product);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void requestFailed() {

            }
        };

        request.requestJsonGET(this, Config.cart+"?cart_id="+cart_id+"&customer_id="+customer_id);

    }

}
