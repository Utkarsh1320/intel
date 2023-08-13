package com.example.intel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Checkout extends AppCompatActivity {

    EditText sd_name,sd_email, sd_address, sd_postal, sd_phone, sd_cardnumber, sd_valid, sd_cvv, sd_holdername;
    TextView txt_Shipping;
    Button orderNow;
    private CardView cardView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);



            sd_name = findViewById(R.id.sd_name);
            sd_email = findViewById(R.id.sd_email);
            sd_address = findViewById(R.id.sd_address);
            sd_postal = findViewById(R.id.sd_postal);
            sd_phone = findViewById(R.id.sd_phone);
            sd_cardnumber = findViewById(R.id.sd_cardnumber);
            sd_valid = findViewById(R.id.sd_valid);
            sd_cvv = findViewById(R.id.sd_cvv);
            sd_holdername = findViewById(R.id.sd_holdername);
            orderNow = findViewById(R.id.checkout_btn);
            cardView = findViewById(R.id.cartActivityCardView);
            txt_Shipping = findViewById(R.id.txt_Shipping);


        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validField = validateForm();
                if (validField) {
                    Intent i = new Intent(Checkout.this, End.class);
                    startActivity(i);
                }
            }
        });

        }

        private boolean validateForm() {
            if (sd_name.length()  == 0 || sd_name.getText().toString().equals(" ") || sd_name.getText().toString().equals("  ") || sd_name.getText().toString().equals("   ")){
                sd_name.setError("Name is required");
                return false;
            }
            String email = sd_email.getText().toString().trim();
            if (email.isEmpty()) {
                sd_email.setError("Email is required");
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                sd_email.setError("Invalid email format");
                return false;
            }
            if (sd_address.length()  == 0 || sd_address.getText().toString().equals(" ") || sd_address.getText().toString().equals("  ") || sd_address.getText().toString().equals("   ")){
                sd_address.setError("Address is required");
                return false;
            }
            String phoneNumber = sd_phone.getText().toString().trim();
            String phonePattern = "\\d{10}";
            if (phoneNumber.length() == 0) {
                sd_phone.setError("Phone number is required");
                return false;
            } else if (!phoneNumber.matches(phonePattern)) {
                sd_phone.setError("Invalid phone number");
                return false;
            }
            if (sd_postal.length() < 6 || sd_postal.getText().toString().equals(" ") || sd_postal.getText().toString().equals("  ") || sd_postal.getText().toString().equals("   ")){
                sd_postal.setError("Enter Valid Postal Code");
            }

            if (sd_holdername.length() == 0 || sd_holdername.getText().toString().equals(" ") || sd_holdername.getText().toString().equals("  ") || sd_holdername.getText().toString().equals("   ")) {
                sd_holdername.setError("Cardholder Name is required");
                return false;
            }
            if (sd_cardnumber.length() == 0 || sd_cardnumber.getText().toString().equals(" ") || sd_cardnumber.getText().toString().equals("  ") || sd_cardnumber.getText().toString().equals("   ")) {
                sd_cardnumber.setError("Card Number is required");
                return false;
            }
            if (sd_cardnumber.length() < 16) {
                sd_cardnumber.setError("Enter 16 Digit Card Number");
                return false;
            }
            if (sd_valid.length() == 0 || sd_valid.getText().toString().equals(" ") || sd_valid.getText().toString().equals("  ") || sd_valid.getText().toString().equals("   ")) {
                sd_valid.setError("Exp Date is required");
                return false;
            }
            if (sd_valid.length() < 4) {
                sd_valid.setError("Expire date is not valid");
                return false;
            }
            if (sd_cvv.length() == 0 || sd_cvv.getText().toString().equals(" ") || sd_cvv.getText().toString().equals("  ") || sd_cvv.getText().toString().equals("   ")) {
                sd_cvv.setError("CVV is required");
                return false;
            }
            if (sd_cvv.length() < 3) {
                sd_cvv.setError("Cvv is Not valid");
                return false;
            }
            return true;
        }
    }


