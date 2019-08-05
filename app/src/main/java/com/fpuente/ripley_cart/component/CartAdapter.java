package com.fpuente.ripley_cart.component;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpuente.ripley_cart.R;
import com.fpuente.ripley_cart.model.Product;

import java.util.ArrayList;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemViewHolder> {
    private List<Product> Items;
    private Context mContext;

    public CartAdapter(Context context) {
        Items = new ArrayList<>();
        mContext = context;
    }

    public CartAdapter(List<Product> items) {
        this.Items = items;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtSKU, txtQuantity, txtPriceList, txtPriceCart;
        ImageView cartIcon , productImg;


        public ItemViewHolder(View view) {
            super(view);
            txtName =  view.findViewById(R.id.txt_name);
            txtSKU  = view.findViewById(R.id.txt_sku);
            txtQuantity = view.findViewById(R.id.txt_quantity);
            txtPriceList = view.findViewById(R.id.txt_list_price);
            txtPriceCart = view.findViewById(R.id.txt_card_price);
            cartIcon     = view.findViewById(R.id.txt_cart);
            productImg   = view.findViewById(R.id.txt_img);




        }

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ItemViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Product model = Items.get(position);

        holder.txtName.setText( model.getName());
        holder.txtSKU.setText( model.getSKU());
        holder.txtPriceList.setText("$"+model.getNormalPrice());
        holder.txtPriceCart.setText("$"+model.getCardPrice());
        if(model.getQuantity() > 1){
            holder.txtQuantity.setText(model.getQuantity()+" productos");
        }else{
            holder.txtQuantity.setText(model.getQuantity()+" producto");
        }

        if(model.getCardPrice() == 0){
            holder.txtPriceCart.setVisibility(View.INVISIBLE);
            holder.cartIcon.setVisibility(View.INVISIBLE);
            holder.txtPriceList.setForeground(null);
        }
        Glide.with(mContext)
                .load(model.getThumbnailImage())
                .centerInside()
                .into(holder.productImg);


    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public void addItems(List<Product> items){
        Items.addAll(items);
        notifyDataSetChanged();
    }

}
