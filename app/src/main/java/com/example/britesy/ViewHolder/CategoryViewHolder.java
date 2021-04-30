package com.example.britesy.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.britesy.Interface.ItemClickListner;
import com.example.britesy.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.cat_image);
        txtProductName = (TextView) itemView.findViewById(R.id.cat_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.cat_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.cat_price);
    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }


    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);

    }
}
