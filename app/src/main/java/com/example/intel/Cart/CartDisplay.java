package com.example.intel.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.checkouts.Checkout;
import com.example.intel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;


public class CartDisplay extends AppCompatActivity {

    Button checkout;
    private FirebaseRecyclerAdapter<CartDataModel, CartViewHolder> adapter;

    private DataSnapshot cartDataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Cart List");

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

            @SuppressLint("RecyclerView")
            @Override
            protected void onBindViewHolder(CartViewHolder holder, int position, CartDataModel model) {
                // Bind data to your ViewHolder
                holder.bindCartItem(model);

                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Increment the quantity in the cart for this item
                        int currentQuantity = Integer.parseInt(holder.productQuantity.getText().toString());
                        currentQuantity++;
                        // Update the Firebase database accordingly
                        holder.productQuantity.setText(String.valueOf(currentQuantity));
                        DatabaseReference cartItemRef = getRef(position);
                        cartItemRef.child("quantity").setValue(String.valueOf(currentQuantity));
                        holder.productQuantity.setText(String.valueOf(currentQuantity));
                        updateTotalPrice();

                    }
                });

                holder.rem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int currentQuantity = Integer.parseInt(holder.productQuantity.getText().toString());
                        if (currentQuantity > 1) {
                            currentQuantity--; // Decrement the quantity
                            holder.productQuantity.setText(String.valueOf(currentQuantity));
                            DatabaseReference cartItemRef = getRef(position);
                            cartItemRef.child("quantity").setValue(String.valueOf(currentQuantity));
                        } else {
                            // If quantity reaches 0, remove the item from the cart list
                            DatabaseReference cartItemRef = getRef(position);
                            cartItemRef.removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Item removed successfully
                                                Toast.makeText(CartDisplay.this, "item Removed", Toast.LENGTH_SHORT).show();
                                            } else {
                                            }
                                        }
                                    });
                        }
                        updateTotalPrice();
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
        updateTotalPrice();

        checkCartEmpty();

        checkout = findViewById(R.id.cartCheckoutBtn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCartEmpty(cartDataSnapshot)) {
                    // Show a toast message if cart is empty
                    Toast.makeText(CartDisplay.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to checkout
                    Intent intent = new Intent(CartDisplay.this, Checkout.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkCartEmpty() {
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Cart List");

        cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartDataSnapshot = dataSnapshot;
                if (isCartEmpty(dataSnapshot)) {
                    // Cart is empty, show the "Your Cart is Empty" text
                    TextView emptyCartTextView = findViewById(R.id.cartEmptyText);
                    emptyCartTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private boolean isCartEmpty(DataSnapshot dataSnapshot) {
        return !dataSnapshot.hasChildren();
    }

    private void updateTotalPrice() {
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Cart List");

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
