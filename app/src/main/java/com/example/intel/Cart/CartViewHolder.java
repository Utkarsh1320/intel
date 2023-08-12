package com.example.intel.Cart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.intel.R;
import com.squareup.picasso.Picasso;

public class CartViewHolder extends RecyclerView.ViewHolder {

    private TextView productName, productPrice, productQuantity;
    private ImageView productImage;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.cart_name);
        productPrice = itemView.findViewById(R.id.cart_price);
        productQuantity = itemView.findViewById(R.id.itemQuantity);
        productImage = itemView.findViewById(R.id.cart_img);
    }

    public void bindCartItem(@NonNull CartDataModel cartItem) {
        productName.setText(cartItem.getHeadline());
        productPrice.setText(cartItem.getPrice());
        productQuantity.setText(cartItem.getQuantity());
        Picasso.get().load(cartItem.getImage()).into(productImage);
    }
}
