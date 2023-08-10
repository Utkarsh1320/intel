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
    private TextView productName;
    private TextView productPrice;
    private TextView productBrand;
    private TextView productDescription;
    DataModel products;
    private final int finalQuantity = 1;
    private final String productID = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_deatils);

        //reference of the database in the activity
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        // ids for elements in the layout
        productImage = findViewById(R.id.detail_img);
        productName = findViewById(R.id.detail_name);
        productPrice = findViewById(R.id.detail_price);
        productBrand = findViewById(R.id.detail_brand);
        productDescription = findViewById(R.id.detail_description);
//        addToCartButton = findViewById(R.id.detailAddToCart);

        //add to cart button ids
        TextView productQuantity = findViewById(R.id.itemQuantity);
        Button addQuantity = findViewById(R.id.addQuantity);
        Button removeQuantity = findViewById(R.id.removeQuantity);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String productId = extras.getString("id");

            assert productId != null;
            productRef.child(productId).get().addOnCompleteListener(task -> {
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


    }



}

