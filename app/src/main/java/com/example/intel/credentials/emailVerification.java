package com.example.intel.credentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.intel.Products.ProductList;
import com.example.intel.R;
import com.example.intel.databinding.ActivityCreatePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class emailVerification extends AppCompatActivity {

    Button verifybtn;
    private String email;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
        }

        mAuth = FirebaseAuth.getInstance();
        verifybtn = findViewById(R.id.btn_verify);
        mUser = mAuth.getCurrentUser();

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationEmail(email);
            }
        });
    }

    private void sendVerificationEmail(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            // The email is not registered, show an error message or redirect to the register page.
                            Toast.makeText(emailVerification.this, "Email is not registered. Please register first.", Toast.LENGTH_SHORT).show();
                        } else {
                            // The email is registered, send verification email
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Toast.makeText(emailVerification.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                                                // Wait for email verification before logging in

                                            } else {
                                                Toast.makeText(emailVerification.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    } else {
                        Toast.makeText(emailVerification.this, "Failed to fetch email information.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            // User is authenticated and email is verified, proceed to the next activity
            redirectToNextActivity();
        }
    }

    private void redirectToNextActivity() {
        Intent intent = new Intent(emailVerification.this, LoginPage.class);
        startActivity(intent);

    }
}
