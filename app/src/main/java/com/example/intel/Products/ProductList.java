package com.example.intel.Products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.intel.Products.ViewHolder.ViewHolder;
import com.example.intel.R;
import com.example.intel.credentials.LoginPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import com.squareup.picasso.Picasso;

public class ProductList extends AppCompatActivity {

    FirebaseAuth auth;

    Button LogoutButton;
    FirebaseUser user;
//    private ImageView addToCartBtn;

    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        auth = FirebaseAuth.getInstance();
        LogoutButton = findViewById(R.id.logout_btn);
        user = auth.getCurrentUser();
//        addToCartBtn = findViewById(R.id.cartImage);


        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        }
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        });


//        addToCartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProductList.this,CartActivity.class);
//                startActivity(intent);
//            }
//        });
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<DataModel> options = new FirebaseRecyclerOptions.Builder<DataModel>()
                .setQuery(ProductRef, DataModel.class)
                .build();

        FirebaseRecyclerAdapter<DataModel, ViewHolder> adapter =
                new FirebaseRecyclerAdapter<DataModel, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull DataModel model) {
                        holder.productName.setText(model.getHeadline());
                        holder.productBrand.setText(model.getBrand());
                        holder.productPrice.setText(model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                        ViewHolder holder = new ViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}