package com.example.intel.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intel.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class CartDisplay extends AppCompatActivity {

    private DatabaseReference cartListRef;
    private FirebaseRecyclerAdapter<CartDataModel, CartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);

        cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View").child("email").child("Products");

        RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CartDataModel> options =
                new FirebaseRecyclerOptions.Builder<CartDataModel>()
                        .setQuery(cartListRef, CartDataModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CartDataModel, CartViewHolder>(options) {
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cartitem, parent, false);
                return new CartViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(CartViewHolder holder, int position, CartDataModel model) {
                // Bind data to your ViewHolder
                holder.bindCartItem(model);
            }
        };
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
