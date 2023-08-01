package com.example.intel.credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.Products.ProductsPage;
import com.example.intel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreatePage extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName;
    Button buttonReg;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView redirect;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), ProductsPage.class);
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
//        editTextFirstName = findViewById(R.id.first_name);
//        editTextLastName = findViewById(R.id.last_name);
        buttonReg = findViewById(R.id.create_btn);
        redirect = findViewById(R.id.loginRedirectText);




        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email , password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());




                if (TextUtils. isEmpty (email)){
                    Toast.makeText(CreatePage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils. isEmpty (password)){
                    Toast.makeText(CreatePage.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreatePage.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(CreatePage.this, emailVerification.class);
                                            startActivity(intent);
                                            finish();
                                } else {
                                    Toast.makeText(CreatePage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}