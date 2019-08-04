package com.fpuente.ripley_cart.component;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpuente.ripley_cart.ProductDetail;
import com.fpuente.ripley_cart.R;
import com.fpuente.ripley_cart.model.Product;
import com.fpuente.ripley_cart.utils.SingletoneRipley;


import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Product> Items = new ArrayList<>();
    OnItemClickListener mItemClickListener;
    Context mContext;

    public ItemAdapter(Context context) {
        Items = new ArrayList<>();
        mContext = context;
    }

    public ItemAdapter(List<Product> items) {
        this.Items = items;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtNormalPrice, txtRipleyPrice;
        ImageView imgProduct, cartIcon;

        public ItemViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            txtName =  view.findViewById(R.id.txt_name);
            txtNormalPrice = view.findViewById(R.id.txt_list_price);
            txtRipleyPrice = view.findViewById(R.id.txt_ripley_price);
            cartIcon       = view.findViewById(R.id.cart_icon_id);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Product model = Items.get(getPosition());
            SingletoneRipley.getInstance(mContext).actualProduct = model;
            Intent registerActivity = new Intent(mContext, ProductDetail.class);
            registerActivity.putExtra("Product",model );
            mContext.startActivity(registerActivity);

        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ItemViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Product model = Items.get(position);

        Glide.with(mContext)
                .load(model.getThumbnailImage())
                .centerInside()
                .into(holder.imgProduct);

        holder.txtName.setText( model.getName());
        holder.txtNormalPrice.setText("$"+model.getNormalPrice());
        holder.txtRipleyPrice.setText("$"+model.getCardPrice());
        if(model.getCardPrice() <= 0){
            holder.txtRipleyPrice.setVisibility(View.INVISIBLE);
            holder.cartIcon.setVisibility(View.INVISIBLE);
            holder.txtNormalPrice.setForeground(null);
        }else{
            holder.txtRipleyPrice.setVisibility(View.VISIBLE);
            holder.cartIcon.setVisibility(View.VISIBLE);
            holder.txtNormalPrice.setForeground(mContext.getDrawable(R.drawable.strike_line));
        }


    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setItems(List<Product> items){
        Items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<Product> items){
        Items.addAll(items);
        notifyDataSetChanged();
    }
    public  void clearItems(){
        Items.clear();
        notifyDataSetChanged();
    }
}
