package com.example.intel.Products.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intel.R;
import com.example.intel.interfac.ItemClickListner;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView productName , productBrand , productPrice;
    public ImageView imageView;
    public ItemClickListner clicklistner;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        productName = (TextView) itemView.findViewById(R.id.Headline);
        productBrand = (TextView) itemView.findViewById(R.id.brand);
        productPrice = (TextView) itemView.findViewById(R.id.price);
    }
    public void setItemClickListner(ItemClickListner listner){
        this.clicklistner = clicklistner;
    }

    @Override
    public void onClick(View v) {
        clicklistner.onlick(v,getAdapterPosition(),false);
    }


}
