package com.example.tmcronin.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmcronin.Interface.ItemClickListener;
import com.example.tmcronin.R;

//from youtube tutorial https://www.youtube.com/watch?v=enyPmr6XhlQ&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=21
//code to get images,description and product name to homepage
public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listner;


    public ProductViewHolder(View itemView) {
        super(itemView);

//information needed to be saved and held
        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }
    public void ItemClickListener (ItemClickListener listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
}
