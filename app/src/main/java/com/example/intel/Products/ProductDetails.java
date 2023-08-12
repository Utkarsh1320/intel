package com.example.intel.Products;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.intel.Cart.CartDataModel;
import com.example.intel.Cart.CartDisplay;
import com.example.intel.R;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.credentials.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.HashMap;


public class ProductDetails extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productBrand;
    private TextView productDescription;
    DataModel products;
    private int finalQuantity = 1;
    private final String productID = " ";
    Button LogoutButton ;
    Button addToCartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_deatils);

        FloatingActionButton cart = findViewById(R.id.cart_btn);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this,CartDisplay.class);
                startActivity(intent);
            }
        });


        //reference of the database in the activity
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        // ids for elements in the layout
        productImage = findViewById(R.id.detail_img);
        productName = findViewById(R.id.detail_name);
        productPrice = findViewById(R.id.detail_price);
        productBrand = findViewById(R.id.detail_brand);
        productDescription = findViewById(R.id.detail_description);
        LogoutButton = findViewById(R.id.logout_btn);

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });

        //add to cart button ids
        addToCartButton = findViewById(R.id.detailAddToCart);

        TextView productQuantity = findViewById(R.id.itemQuantity);
        Button addQuantity = findViewById(R.id.addQuantity);
        Button removeQuantity = findViewById(R.id.removeQuantity);
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
                    return;
                }
                finalQuantity--;
                productQuantity.setText(String.valueOf(finalQuantity));
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String productID = extras.getString("id");

            assert productID != null;
            productRef.child(productID).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    DataModel product = task.getResult().getValue(DataModel.class);
                    if(product!=null){
                        setTitle(product.getHeadline());
                        productName.setText(product.getHeadline());
                        productPrice.setText(product.getPrice());
                        productBrand.setText(product.getBrand());
                        productDescription.setText(product.getDescription());
                        Picasso.get().load(product.getImage()).into(productImage);
                    }
                }

            });
        }
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("id", productID);
                cartMap.put("headline", productName.getText().toString());
                cartMap.put("price", productPrice.getText().toString());
                cartMap.put("brand", productBrand.getText().toString());
                cartMap.put("quantity", String.valueOf(finalQuantity));
                cartMap.put("image", products.getImage());


                cartListRef.child("User View").child("email")
                        .child("Products").child(productID)
                        .updateChildren(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(ProductDetails.this, "Item added to cart", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(ProductDetails.this, ProductList.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
        productQuantity.setText(String.valueOf(finalQuantity));

    }
}

