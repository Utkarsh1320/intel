package com.example.intel.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.intel.Checkout;
import com.example.intel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;


public class CartDisplay extends AppCompatActivity {

    ImageButton add, rem ;

    Button checkout;

    private FirebaseRecyclerAdapter<CartDataModel, CartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");



        RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
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
        updateTotalPrice();


        checkout = findViewById(R.id.cartCheckoutBtn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartDisplay.this, Checkout.class);
                startActivity(intent);
            }
        });
    }







    private void updateTotalPrice() {
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalPrice = 0;

                for (DataSnapshot cartItemSnapshot : dataSnapshot.getChildren()) {
                    CartDataModel cartItem = cartItemSnapshot.getValue(CartDataModel.class);
                    int itemPrice = Integer.parseInt(cartItem.getPrice());
                    int itemQuantity = Integer.parseInt(cartItem.getQuantity());
                    totalPrice += itemPrice * itemQuantity;
                }

                TextView totalPriceTextView = findViewById(R.id.cartTotalPrice);
                totalPriceTextView.setText("Total Price: $" + totalPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }






    @Override
    protected void onStart() {
        super.onStart();
        updateTotalPrice();
        adapter.startListening();




    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
