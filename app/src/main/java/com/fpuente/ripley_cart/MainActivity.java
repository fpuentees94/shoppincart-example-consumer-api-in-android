package com.fpuente.ripley_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fpuente.ripley_cart.api.ApiRestAppKeep;
import com.fpuente.ripley_cart.api.AsyncTaskResult;
import com.fpuente.ripley_cart.component.ItemAdapter;
import com.fpuente.ripley_cart.model.Attribute;
import com.fpuente.ripley_cart.model.Category;
import com.fpuente.ripley_cart.model.Product;
import com.fpuente.ripley_cart.utils.CategoriesData;
import com.fpuente.ripley_cart.utils.ServiceUtils;
import com.fpuente.ripley_cart.utils.SingletoneRipley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Context context;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private ProgressBar progressBar;
    private boolean isLoading = false;
    private String filter ="";
    private ArrayList<Category>originalCategories = CategoriesData.dataCategories();
    private ArrayList<Category>filterCategories = new ArrayList<>();
    private String checked = "all";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        try {


            context = this;
            mRecyclerView = findViewById(R.id.recycler_view);
            progressBar = findViewById(R.id.progress_bar);

            Toolbar toolbar = findViewById(R.id.toolbar_home);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);

            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) context);

            MenuItem menuItem = navigationView.getMenu().findItem(R.id.tecno);
            CompoundButton compundButton = (CompoundButton) menuItem.getActionView();
            compundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        for (Category c: originalCategories) {
                            if(c.getName().equals("tecno")){
                                Log.d("tecno",c.getName());
                                filterCategories.add(c);
                            }

                        }
                    }else{
                        ArrayList<Category> remove = new ArrayList<>();
                        for (Category e: filterCategories) {
                            if (e.getName().equals("tecno")){
                                remove.add(e);
                            }

                        }
                        filterCategories.removeAll(remove);


                    }
                    String categories = "";
                    for (int i = 0; i < filterCategories.size(); i++){
                        Category category = filterCategories.get(i);
                        if(i == filterCategories.size()-1){
                            categories += category.getSKU();
                        }else{
                            categories += category.getSKU()+",";
                        }

                    }
                    if(categories.equals("")){
                        for (int i = 0; i < originalCategories.size(); i++){
                            Category category = originalCategories.get(i);
                            if(i == originalCategories.size()-1){
                                categories += category.getSKU();
                            }else{
                                categories += category.getSKU()+",";
                            }

                        }
                    }
                    getProduct(categories);


                }
            });
            MenuItem menuItem0 = navigationView.getMenu().findItem(R.id.elect);
            CompoundButton compundButton0 = (CompoundButton) menuItem0.getActionView();
            compundButton0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.d("CATEGORIAS",""+filterCategories.size());
                    if (b){
                        for (Category c: originalCategories) {
                            if(c.getName().equals("electro")){
                                filterCategories.add(c);
                            }

                        }
                    }else{

                        ArrayList<Category> remove = new ArrayList<>();
                        for (Category e: filterCategories) {
                            if (e.getName().equals("electro")){
                                remove.add(e);
                            }

                        }
                        filterCategories.removeAll(remove);


                    }
                    String categories = "";
                    for (int i = 0; i < filterCategories.size(); i++){
                        Category category = filterCategories.get(i);
                        if(i == filterCategories.size()-1){
                            categories += category.getSKU();
                        }else{
                            categories += category.getSKU()+",";
                        }

                    }
                    if(categories.equals("")){
                        for (int i = 0; i < originalCategories.size(); i++){
                            Category category = originalCategories.get(i);
                            if(i == originalCategories.size()-1){
                                categories += category.getSKU();
                            }else{
                                categories += category.getSKU()+",";
                            }

                        }
                    }
                    getProduct(categories);

                }
            });
            MenuItem menuItem1 = navigationView.getMenu().findItem(R.id.home);
            CompoundButton compundButton1 = (CompoundButton) menuItem1.getActionView();
            compundButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        for (Category c: originalCategories) {
                            if(c.getName().equals("home")){
                                filterCategories.add(c);
                            }

                        }
                    }else{

                        ArrayList<Category> remove = new ArrayList<>();
                        for (Category e: filterCategories) {
                            if (e.getName().equals("home")){
                                remove.add(e);
                            }

                        }

                        filterCategories.removeAll(remove);


                    }
                    String categories = "";
                    for (int i = 0; i < filterCategories.size(); i++){
                        Category category = filterCategories.get(i);
                        if(i == filterCategories.size()-1){
                            categories += category.getSKU();
                        }else{
                            categories += category.getSKU()+",";
                        }

                    }
                    if(categories.equals("")){
                        for (int i = 0; i < originalCategories.size(); i++){
                            Category category = originalCategories.get(i);
                            if(i == originalCategories.size()-1){
                                categories += category.getSKU();
                            }else{
                                categories += category.getSKU()+",";
                            }

                        }
                    }
                    getProduct(categories);

                }
            });
            MenuItem menuItem2 = navigationView.getMenu().findItem(R.id.woman);
            CompoundButton compundButton2 = (CompoundButton) menuItem2.getActionView();
            compundButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        for (Category c: originalCategories) {
                            if(c.getName().equals("woman")){
                                filterCategories.add(c);
                            }

                        }
                    }else{

                        ArrayList<Category> remove = new ArrayList<>();
                        for (Category e: filterCategories) {
                            if (e.getName().equals("woman")){
                                remove.add(e);
                            }

                        }
                        filterCategories.removeAll(remove);


                    }
                    String categories = "";
                    for (int i = 0; i < filterCategories.size(); i++){
                        Category category = filterCategories.get(i);
                        if(i == filterCategories.size()-1){
                            categories += category.getSKU();
                        }else{
                            categories += category.getSKU()+",";
                        }

                    }
                    if(categories.equals("")){
                        for (int i = 0; i < originalCategories.size(); i++){
                            Category category = originalCategories.get(i);
                            if(i == originalCategories.size()-1){
                                categories += category.getSKU();
                            }else{
                                categories += category.getSKU()+",";
                            }

                        }
                    }
                    getProduct(categories);

                }
            });
            MenuItem menuItem3 = navigationView.getMenu().findItem(R.id.sports);
            CompoundButton compundButton3 = (CompoundButton) menuItem3.getActionView();
            compundButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        for (Category c: originalCategories) {
                            if(c.getName().equals("sports")){
                                filterCategories.add(c);
                            }

                        }
                    }else{

                        ArrayList<Category> remove = new ArrayList<>();
                        for (Category e: filterCategories) {
                            if (e.getName().equals("sports")){
                                remove.add(e);
                            }

                        }
                        filterCategories.removeAll(remove);


                    }
                    String categories = "";
                    for (int i = 0; i < filterCategories.size(); i++){
                        Category category = filterCategories.get(i);
                        if(i == filterCategories.size()-1){
                            categories += category.getSKU();
                        }else{
                            categories += category.getSKU()+",";
                        }

                    }
                    if(categories.equals("")){
                        for (int i = 0; i < originalCategories.size(); i++){
                            Category category = originalCategories.get(i);
                            if(i == originalCategories.size()-1){
                                categories += category.getSKU();
                            }else{
                                categories += category.getSKU()+",";
                            }

                        }
                    }
                    getProduct(categories);

                }
            });



            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(mLayoutManager);


            mAdapter = new ItemAdapter(context);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClicklListener(new ItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {


                    Toast.makeText(context, "Clicked item position: " + position, Toast.LENGTH_LONG).show();
                }
            });

            getProduct("2000371667503P,2000351773811P,2000369109855P,2000366737198P,2000372411631P,2000359329935P,2000375421729P,2000372107077P,2000371958953P,2000373857964P,2000371827983P,2000374299572P,2000374310932P,2000368425048P,2000371915604P,2000369989594P,MPM00002006512,2000335659285P,2000327637482P,MPM00001075806,2000370840266,2000371979422,2000373199514,2000373423879,2000372471789");

        } catch (Exception ex) {
            Log.e("ERRORRR", ex.getMessage());
        }


    }


    private void getProduct(final String products) {

        progressBar.setVisibility(View.VISIBLE);

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
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // Stuff that updates the UI
                                        resultAction(products);

                                    }
                                });




                                break;
                            default:

                                break;
                        }}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void resultAction(ArrayList<Product> products) {
        progressBar.setVisibility(View.INVISIBLE);
        isLoading = false;
        if (products != null) {
            mAdapter.clearItems();
            mAdapter.addItems(products);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SingletoneRipley singletoneRipley = SingletoneRipley.getInstance(context);
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(ServiceUtils.convertLayoutToImage(MainActivity.this,
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
    protected void onResume() {
        super.onResume();
        ActivityCompat.invalidateOptionsMenu(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);

    }
}
