
package com.example.intel.credentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.intel.Products.ProductList;
import com.example.intel.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreatePage extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_page);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        buttonReg = findViewById(R.id.create_btn);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etEmail = editTextEmail.getText().toString();
                String etPassword = editTextPassword.getText().toString();

                if (validateEmailAndPassword(etEmail, etPassword)) {
                    registerUser(etEmail, etPassword);
                }
            }
        });
    }

    private boolean validateEmailAndPassword(String email, String password) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email address");
            return false;
        }

        if (password.isEmpty() || password.length() < 8) {
            editTextPassword.setError("Password must be at least 8 characters");
            return false;
        }

        return true;
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            redirectToVerificationActivity(email);
                        }
                    } else {
                        Toast.makeText(CreatePage.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void redirectToVerificationActivity(String email) {
        Intent intent = new Intent(CreatePage.this, emailVerification.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}
