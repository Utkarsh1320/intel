package com.example.intel.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.intel.Checkout;
import com.example.intel.Products.ProductDetails;
import com.example.intel.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;

    private  int TotalPrice=0;
    public TextView txtTotalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Button nextProcessBtn = findViewById(R.id.cartCheckoutBtn);
        txtTotalAmount = findViewById(R.id.cartTotalPrice);

        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartDisplay.this, Checkout.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<CartDataModel> options = new FirebaseRecyclerOptions.Builder<CartDataModel>()
                .setQuery(cartListRef.child("User View")
                        .child("email")
                        .child("Products"), CartDataModel.class)
                .build();

        FirebaseRecyclerAdapter<CartDataModel, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<CartDataModel, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartDataModel model) {
                holder.txtProductName.setText(model.getHeadline());
                holder.txtProductPrice.setText(model.getPrice());
                holder.itemQuantity.setText("Quantity: " + model.getQuantity());
                Picasso.get().load(model.getImage()).into(holder.cartImage);




                int ProductPrice=((Integer.valueOf(model.getPrice()))*Integer.valueOf(model.getQuantity()));
                TotalPrice=TotalPrice+ProductPrice;
                txtTotalAmount.setText(String.valueOf(TotalPrice));


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence[] options = new CharSequence[]
                                {
                                        "Edit","Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartDisplay.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                if(i==0) {
                                    Intent intent = new Intent(CartDisplay.this, ProductDetails.class);
                                    intent.putExtra("id", model.getId());
                                    startActivity(intent);
                                }

                                if(i==1){
                                    cartListRef.child("User View")
                                            .child("email")

                                            .child("Products")

                                            .child(model.getId())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(CartDisplay.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}