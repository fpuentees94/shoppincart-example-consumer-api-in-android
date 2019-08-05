package com.fpuente.ripley_cart.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpuente.ripley_cart.R;
import com.fpuente.ripley_cart.model.Attribute;

import java.util.ArrayList;
import java.util.List;


public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ItemViewHolder> {
    private List<Attribute> Items;
    private Context mContext;

    public AttributesAdapter(Context context) {
        Items = new ArrayList<>();
        mContext = context;
    }

    public AttributesAdapter(List<Attribute> items) {
        this.Items = items;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtValue;
        RelativeLayout attribute;


        ItemViewHolder(View view) {
            super(view);
            txtName =  view.findViewById(R.id.txt_name);
            txtValue = view.findViewById(R.id.txt_value);
            attribute    = view.findViewById(R.id.cardview_attribute);

        }

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.atribute, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Attribute model = Items.get(position);
        holder.txtName.setText( model.getName());
        holder.txtValue.setText( model.getValue());
        if(position % 2 == 0){
            holder.attribute.setBackgroundColor(mContext.getResources().getColor(R.color.colorLead));
        }else{
            holder.attribute.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public void addItems(List<Attribute> items){
        Items.addAll(items);
        notifyDataSetChanged();
    }

}
