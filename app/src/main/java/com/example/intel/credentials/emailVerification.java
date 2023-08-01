package com.example.intel.credentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.intel.R;
import com.example.intel.databinding.ActivityCreatePageBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class emailVerification extends AppCompatActivity {

    Button verifybtn;
    ActivityCreatePageBinding binding;
    FirebaseAuth mAuth;
    private String email , password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        binding = ActivityCreatePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        verifybtn = findViewById(R.id.btn_verify);

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = binding.emailInput.getText().toString();
                password = binding.passwordInput.getText().toString();
                mAuth.getInstance()
                        .signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                if(user.isEmailVerified()){
                                    Intent intent = new Intent(emailVerification.this, LoginPage.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    user.sendEmailVerification();
                                }
                            }
                        });
            }
        });
    }
}