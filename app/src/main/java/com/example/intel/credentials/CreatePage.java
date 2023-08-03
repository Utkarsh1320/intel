package com.example.intel.credentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.Products.ProductList;
import com.example.intel.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreatePage extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword,editTextFirstName, editTextLastName;
    Button buttonReg;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView redirect;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), ProductList.class);
            startActivity(intent);
            finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_page);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        buttonReg = findViewById(R.id.create_btn);
        redirect = findViewById(R.id.loginRedirectText);


        buttonReg.setOnClickListener(new View.OnClickListener() {
            public boolean isValidEmail(String email) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                return email.matches(emailPattern);
            }
            @Override
            public void onClick(View v) {
                String etEmail , etPassword;
                etEmail = String.valueOf(editTextEmail.getText());
                etPassword = String.valueOf(editTextPassword.getText());

                if (validateEmailAndPassword(etEmail, etPassword)) {
                    registerUser(etEmail, etPassword);
                }
//                if (TextUtils. isEmpty (password)){
//                    Toast.makeText(CreatePage.this, "Enter password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(CreatePage.this, "Account Created.",
//                                            Toast.LENGTH_SHORT).show();
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Intent intent = new Intent(CreatePage.this, emailVerification.class);
//                                    startActivity(intent);
//                                    finish();
////                                    Intent intent = new Intent(CreatePage.this, emailVerification.class);
////                                    startActivity(intent);
////                                    finish();
//                                } else {
//                                    Toast.makeText(CreatePage.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
            }
        });
//        redirect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CreatePage.this, LoginPage.class);
//                startActivity(intent);
//                finish();
//            }
//        });
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
                            // Redirect to the next activity (e.g., MainActivity)
                            redirectToNextActivity();
                        }
                    } else {
                        Toast.makeText(CreatePage.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void redirectToNextActivity() {
        Intent intent = new Intent(CreatePage.this, emailVerification.class);
        startActivity(intent);
        finish();
    }

}