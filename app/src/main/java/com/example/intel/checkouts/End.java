package com.example.intel.checkouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.intel.Products.ProductList;
import com.example.intel.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class End extends AppCompatActivity {

    Button end ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        end = findViewById(R.id.back);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
                Intent i = new Intent(End.this, ProductList.class);
                startActivity(i);
            }

        });


    }

    private void clearCart() {
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        cartListRef.removeValue();
    }
}