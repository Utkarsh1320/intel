package com.example.intel.Products;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.intel.R;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;




public class ProductDetails extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productBrand, productDescription, productQuantity;
    private Button  addQuantity, removeQuantity;
    DataModel products;
    private int finalQuantity = 1;
    private String productID = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_deatils);

        productID = getIntent().getStringExtra("pid");
        productImage = findViewById(R.id.detail_img);
        productName = findViewById(R.id.detail_name);
        productPrice = findViewById(R.id.detail_price);
        productBrand = findViewById(R.id.detail_brand);
        productDescription = findViewById(R.id.detail_description);
//        addToCartButton = findViewById(R.id.detailAddToCart);
        productQuantity = findViewById(R.id.itemQuantity);
        addQuantity = findViewById(R.id.addQuantity);
        removeQuantity = findViewById(R.id.removeQuantity);
        getProductDetails(productID);

    }
    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    products = snapshot.getValue(DataModel.class);
                    productName.setText(products.getHeadline());
                    productPrice.setText(products.getPrice());
                    productBrand.setText(products.getBrand());
                    productDescription.setText(products.getDescription());
                    productQuantity.setText(String.valueOf(finalQuantity));
                    Picasso.get().load(products.getImage()).into(productImage);


                    addQuantity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalQuantity++;
                            productQuantity.setText(String.valueOf(finalQuantity));
                        }
                    });

                    removeQuantity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(finalQuantity == 1){
                                Log.d("final", String.valueOf(finalQuantity));
                                return;
                            }
                            finalQuantity--;
                            productQuantity.setText(String.valueOf(finalQuantity));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

