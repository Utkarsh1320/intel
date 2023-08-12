package com.example.intel.Cart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intel.R;
import com.example.intel.interfac.ItemClickListner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductBrand, itemQuantity, totalPrice;
    public ImageView cartImage;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_name);
        txtProductPrice = itemView.findViewById(R.id.price);
        cartImage = itemView.findViewById(R.id.cart_img);
        itemQuantity = itemView.findViewById(R.id.itemQuantity);
        totalPrice = itemView.findViewById(R.id.cartTotalPrice);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onlick(itemView, getAdapterPosition(),false);
    }
    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
