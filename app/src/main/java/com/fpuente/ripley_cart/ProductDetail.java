package com.fpuente.ripley_cart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fpuente.ripley_cart.api.ApiRestAppKeep;
import com.fpuente.ripley_cart.api.AsyncTaskResult;
import com.fpuente.ripley_cart.api.VolleyService;
import com.fpuente.ripley_cart.component.AttributesAdapter;
import com.fpuente.ripley_cart.config.Config;
import com.fpuente.ripley_cart.model.Attribute;
import com.fpuente.ripley_cart.model.Product;
import com.fpuente.ripley_cart.preferences.PreferenceCart;
import com.fpuente.ripley_cart.utils.ServiceUtils;
import com.fpuente.ripley_cart.utils.SingletoneRipley;
import com.google.android.material.snackbar.Snackbar;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDetail extends AppCompatActivity {


    CarouselView carouselView;
    ArrayList<String> images;
    private RecyclerView mRecyclerView;
    private AttributesAdapter mAdapter;
    Button addProduct;
    private PreferenceCart preferenceCart;
    TextView priceCart, priceNormal, name;
    ImageView cart;
    Product productItem;
    SingletoneRipley singletoneRipley;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Product product = (Product) getIntent().getSerializableExtra("Product");

        getProduct(product.getSKU());
        images = product.getImages();

        singletoneRipley = SingletoneRipley.getInstance(this);

        this.preferenceCart = new PreferenceCart(this);

        priceCart = findViewById(R.id.txt_card_price);
        priceNormal = findViewById(R.id.txt_list_price);
        name   = findViewById(R.id.txt_name);
        cart   = findViewById(R.id.txt_cart);

        priceCart.setText("$"+product.getCardPrice());
        name.setText(product.getName());
        priceNormal.setText("$"+product.getNormalPrice());

        if(product.getCardPrice() == 0){
            priceCart.setVisibility(View.INVISIBLE);
            cart.setVisibility(View.INVISIBLE);
            priceNormal.setForeground(null);
        }
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(images.size());
        carouselView.setImageListener(imageListener);
        mRecyclerView = findViewById(R.id.recycler_view_attributes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new AttributesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addItems(product.getAttributes());
        addProduct = findViewById(R.id.add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(singletoneRipley.products.contains(singletoneRipley.actualProduct)){
                    for (Product p : singletoneRipley.products) {
                        if(singletoneRipley.actualProduct.getSKU() == p.getSKU()){
                            p.setQuantity(p.getQuantity()+1);

                        }

                    }
                }else{
                    singletoneRipley.actualProduct.setQuantity(1);
                    singletoneRipley.products.add(singletoneRipley.actualProduct);
                }

                Snackbar.make(view, "¡Producto agregado a la bolsa!", Snackbar.LENGTH_SHORT)
                        .show();
                ActivityCompat.invalidateOptionsMenu(ProductDetail.this);

                    if(!preferenceCart.getKeyCartId().equals("")){

                        addProductToCart(ServiceUtils.shortUUID(),preferenceCart.getKeyCartId(),product.getSKU(),
                        product.getCardPrice(), product.getQuantity());


                    }else{
                        createCart(ServiceUtils.shortUUID(),product.getSKU(),product.getCardPrice(),
                                product.getQuantity());
                    }

            }
        });

    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, final ImageView imageView) {


            Glide.with(getApplicationContext())
                    .asBitmap()
                    .centerInside()
                    .load("https:"+images.get(position))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                        }
                    });
        }
    };


    private void  createCart(final String customer_id, final String sku, final int price, final int quantity) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRestAppKeep apiRestAppKeep = new ApiRestAppKeep();
                try {
                    AsyncTaskResult<JSONObject> asyncTaskResult = apiRestAppKeep.createCart(customer_id);

                    if (asyncTaskResult.getError() != null) {

                        final String msg = "Valide su conexión a internet, no se puede cargar datos iniciales.";
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else
                    {

                        JSONObject result = asyncTaskResult.getResult();
                        int code = result.getInt("code");

                        switch (code) {
                            case 200:
                                JSONObject body = (JSONObject) result.get("body");
                                preferenceCart.setKeyCartId(body.getString("cart_id"));
                                preferenceCart.setKeyCustomerId(body.getString("customer_id"));
                                addProductToCart(customer_id,preferenceCart.getKeyCartId(),sku,price,quantity);

                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Error obteniendo datos iniciales de usuario\ncode: [" + code + "]", Toast.LENGTH_SHORT).show();
                                break;
                        }}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void addProductToCart(String customer_id, String cart_id, String sku, final int price, int quantity){
        try {
            VolleyService request = new VolleyService() {
                @Override
                public void requestSuccessful(JSONObject j) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateCart(price);
                        }
                    });


                }

                @Override
                public void requestFailed() {

                }
            };
            JSONObject productJson = new JSONObject();
            productJson.put("price", String.valueOf(price));
            productJson.put("sku", sku);
            productJson.put("quantity", String.valueOf(quantity));
            JSONArray products = new JSONArray();
            products.put(productJson);

            JSONObject hs = new JSONObject();
            hs.put("customer_id",customer_id);
            hs.put("customer_id", customer_id);
            hs.put("cart_id", cart_id);
            hs.put("products", products);

            request.requestsJsonPOST(this, Config.products, hs);
        }catch (Exception e){

        }

    }

    public void updateCart(int price){

        int newPrice = preferenceCart.getKeyTotalPrice() + price;
        try {
            VolleyService request = new VolleyService() {
                @Override
                public void requestSuccessful(JSONObject j) {

                    try {
                        Log.d("total_price",j.toString());
                        preferenceCart.setKeyTotalPrice(j.getInt("total_price"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestFailed() {

                }
            };

            JSONObject hs = new JSONObject();
            hs.put("customer_id",preferenceCart.getKeyCustomerId());
            hs.put("cart_id", preferenceCart.getKeyCartId());
            hs.put("total_price",newPrice );

            request.requestsJsonPUT(this, Config.cart, hs);
        }catch (Exception e){

        }

    }

    private void getProduct(final String products) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRestAppKeep apiRestAppKeep = new ApiRestAppKeep();
                try {
                    AsyncTaskResult<JSONObject> asyncTaskResult = apiRestAppKeep.getProducts(products);

                    if (asyncTaskResult.getError() != null) {

                        final String msg = "Valide su conexión a internet, no se puede cargar datos iniciales.";
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else
                    {

                        JSONObject result = asyncTaskResult.getResult();
                        int code = result.getInt("code");

                        switch (code) {
                            case 200:
                                JSONArray body = (JSONArray) result.get("body");
                                final ArrayList<Product> products = new ArrayList<Product>();
                                for(int i = 0; i< body.length(); i++){
                                    JSONObject product = body.getJSONObject(i);
                                    JSONObject prices = product.getJSONObject("prices");
                                    Product newProduct = new Product();
                                    newProduct.setSKU(product.getString("partNumber"));
                                    newProduct.setName(product.getString("name"));
                                    newProduct.setThumbnailImage("https:"+product.getString("thumbnailImage"));
                                    if(prices.has("cardPrice")){
                                        newProduct.setCardPrice(prices.getInt("cardPrice"));}
                                    newProduct.setNormalPrice(prices.getInt("listPrice"));

                                    JSONArray images = (JSONArray) product.get("images");
                                    ArrayList<String> aImages = new ArrayList<String>();
                                    for (int j = 0; j<images.length(); j++) {
                                        aImages.add( images.getString(j) );
                                    }
                                    JSONArray attributesJSON = product.getJSONArray("attributes");
                                    ArrayList<Attribute> attributes = new ArrayList<>();
                                    for (int k = 0 ; k < attributesJSON.length(); k++){
                                        JSONObject attribute = attributesJSON.getJSONObject(k);
                                        Attribute newAttribute = new Attribute();
                                        newAttribute.setName(attribute.getString("name"));
                                        newAttribute.setValue(attribute.getString("value"));
                                        attributes.add(newAttribute);

                                    }
                                    newProduct.setAttributes(attributes);
                                    newProduct.setImages(aImages);
                                    products.add(newProduct);

                                }
                                productItem =products.get(0);





                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Error obteniendo datos iniciales de usuario\ncode: [" + code + "]", Toast.LENGTH_SHORT).show();
                                break;
                        }}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SingletoneRipley singletoneRipley = SingletoneRipley.getInstance(this);
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(ServiceUtils.convertLayoutToImage(ProductDetail.this,
                singletoneRipley.totalProductsInCart(),R.drawable.cart));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.cart_action){
            Intent cartActivity = new Intent(this, Cart.class);
            startActivity(cartActivity);

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
