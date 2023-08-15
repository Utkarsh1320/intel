package com.example.intel.checkouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.intel.R;

public class Checkout extends AppCompatActivity {

    EditText name, email, address, code, phone, sd_cardnumber, sd_valid, sd_cvv, sd_holdername;
    TextView txt_Shipping;
    Button orderNow;
    private CardView cardView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);



            name = findViewById(R.id.sd_name);
            email = findViewById(R.id.sd_email);
            address = findViewById(R.id.sd_address);
            code = findViewById(R.id.sd_postal);
            phone = findViewById(R.id.sd_phone);
            orderNow = findViewById(R.id.checkout_btn);




        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validField = validateForm();
                if (validField) {
                    Intent i = new Intent(Checkout.this, Payment.class);
                    startActivity(i);
                }
            }
        });

        }

        private boolean validateForm() {
            if (name.length()  == 0 || name.getText().toString().equals(" ") || name.getText().toString().equals("  ") || name.getText().toString().equals("   ")){
                name.setError("Name is required");
                return false;
            }
            String email = this.email.getText().toString().trim();
            if (email.isEmpty()) {
                this.email.setError("Email is required");
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                this.email.setError("Invalid email format");
                return false;
            }
            if (address.length()  == 0 || address.getText().toString().equals(" ") || address.getText().toString().equals("  ") || address.getText().toString().equals("   ")){
                address.setError("Address is required");
                return false;
            }
            String phoneNumber = phone.getText().toString().trim();
            String phonePattern = "\\d{10}";
            if (phoneNumber.length() == 0) {
                phone.setError("Phone number is required");
                return false;
            } else if (!phoneNumber.matches(phonePattern)) {
                phone.setError("Invalid phone number");
                return false;
            }
            if (code.length() < 6 || code.getText().toString().equals(" ") || code.getText().toString().equals("  ") || code.getText().toString().equals("   ")){
                code.setError("Enter Valid Postal Code");
            }


            return true;
        }
    }



