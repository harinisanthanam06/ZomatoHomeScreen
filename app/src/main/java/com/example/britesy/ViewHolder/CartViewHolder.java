package com.example.britesy.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.britesy.Interface.ItemClickListner;
import com.example.britesy.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView textProductName,textProductPrice,textProductquantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        textProductName=itemView.findViewById(R.id.cart_art_name);
        textProductPrice=itemView.findViewById(R.id.cart_art_price);
        textProductquantity=itemView.findViewById(R.id.cart_art_quantity);

    }


    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
