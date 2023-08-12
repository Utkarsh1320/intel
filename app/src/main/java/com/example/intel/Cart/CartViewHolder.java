package com.example.intel.Cart;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.intel.R;
import com.squareup.picasso.Picasso;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView productName, productPrice, productQuantity;
    public ImageView productImage;

    ImageButton add, rem;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.cart_name);
        productPrice = itemView.findViewById(R.id.cart_price);
        productQuantity = itemView.findViewById(R.id.itemQuantity);
        productImage = itemView.findViewById(R.id.cart_img);
        add = itemView.findViewById(R.id.additem);
        rem = itemView.findViewById(R.id.removeitem);
    }

    public void bindCartItem(@NonNull CartDataModel cartItem) {
        productName.setText(cartItem.getHeadline());
        productPrice.setText(cartItem.getPrice());
        productQuantity.setText(cartItem.getQuantity());
        Picasso.get().load(cartItem.getImage()).into(productImage);
    }
}
