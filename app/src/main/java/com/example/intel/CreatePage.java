package com.example.intel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuthException;

public class CreatePage extends AppCompatActivity {
    private FirebaseAuthException auth;

    private EditText signupEmail , signupPassword;
    private Button signupButton;
    private TextView loginReditectText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_page);

    }
}