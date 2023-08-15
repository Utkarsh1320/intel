package com.example.intel.checkouts;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intel.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Payment extends AppCompatActivity {

    private LinearLayout creditDebitLayout, netBankingLayout, podLayout;
    private EditText cardHolderEditText, cardNumberEditText, cvvEditText, expiryEditText;
    private EditText usernameEditText, passwordEditText;
    private RadioButton radioPODCash, radioPODCard;
    private TextView selectPaymentMethodText;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize UI elements
        RadioGroup paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        creditDebitLayout = findViewById(R.id.creditDebitLayout);
        netBankingLayout = findViewById(R.id.netBankingLayout);
        podLayout = findViewById(R.id.podLayout);
        cardHolderEditText = findViewById(R.id.cardHolderEditText);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
        expiryEditText = findViewById(R.id.expiryEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        radioPODCash = findViewById(R.id.radioPODCash);
        radioPODCard = findViewById(R.id.radioPODCard);
        selectPaymentMethodText = findViewById(R.id.selectPaymentMethodText);
        submit = findViewById(R.id.submitButton);

        cvvEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check and limit the CVV to 3 digits
                if (s.length() > 3) {
                    cvvEditText.setText(s.subSequence(0, 3));
                    cvvEditText.setSelection(3);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        cardNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String formattedCardNumber = formatCardNumber(s.toString());
                cardNumberEditText.removeTextChangedListener(this);
                cardNumberEditText.setText(formattedCardNumber);
                cardNumberEditText.setSelection(formattedCardNumber.length());
                cardNumberEditText.addTextChangedListener(this);

                if (formattedCardNumber.length() > 19) {
                    cardNumberEditText.setText(formattedCardNumber.substring(0, 19));
                    cardNumberEditText.setSelection(19);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        expiryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String formattedExpiryDate = formatExpiryDate(s.toString());
                expiryEditText.removeTextChangedListener(this);
                expiryEditText.setText(formattedExpiryDate);
                expiryEditText.setSelection(formattedExpiryDate.length());
                expiryEditText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        paymentMethodRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioCreditDebit ||
                    checkedId == R.id.radioNetBanking ||
                    checkedId == R.id.radioPOD) {
                selectPaymentMethodText.setVisibility(View.INVISIBLE);
            }
            if (checkedId == R.id.radioCreditDebit) {
                creditDebitLayout.setVisibility(View.VISIBLE);
                netBankingLayout.setVisibility(View.GONE);
                podLayout.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioNetBanking) {
                creditDebitLayout.setVisibility(View.GONE);
                netBankingLayout.setVisibility(View.VISIBLE);
                podLayout.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioPOD) {
                creditDebitLayout.setVisibility(View.GONE);
                netBankingLayout.setVisibility(View.GONE);
                podLayout.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(v -> {
            if (creditDebitLayout.getVisibility() == View.VISIBLE) {
                String cardHolder = cardHolderEditText.getText().toString().trim();
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String cvv = cvvEditText.getText().toString().trim();
                String expiry = expiryEditText.getText().toString().trim();

                if (TextUtils.isEmpty(cardHolder) || TextUtils.isEmpty(cardNumber) ||
                        TextUtils.isEmpty(cvv) || TextUtils.isEmpty(expiry)) {
                    showToast("Please fill all card details");
                } else if (!isValidCardNumber(cardNumber)) {
                    showToast("Invalid card number");
                } else if (!isValidCVV(cvv)) {
                    showToast("Invalid CVV");
                } else if (!isValidExpiryDate(expiry)) {
                    showToast("Invalid expiry date");
                } else {
                    startActivity(new Intent(Payment.this, End.class));
                }
            } else if (netBankingLayout.getVisibility() == View.VISIBLE) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    showToast("Please fill both username and password");
                } else {
                    startActivity(new Intent(Payment.this, End.class));
                }
            } else if (podLayout.getVisibility() == View.VISIBLE) {
                if (radioPODCash.isChecked()) {
                    showToast("Cash on Delivery selected");
                    Intent i = new Intent(Payment.this, End.class);
                    startActivity(i);
                } else if (radioPODCard.isChecked()) {
                    showToast("Card on Delivery selected");
                    Intent i = new Intent(Payment.this, End.class);
                    startActivity(i);
                } else {
                    showToast("Please select a POD option");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() == 19;
    }

    private boolean isValidCVV(String cvv) {
        return cvv.length() == 3;
    }

    private boolean isValidExpiryDate(String expiryDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);
        dateFormat.setLenient(false);

        try {
            Date currentDate = Calendar.getInstance().getTime();
            Date inputDate = dateFormat.parse(expiryDate);

            return inputDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    private String formatCardNumber(String input) {
        String digits = input.replaceAll("\\D", "");

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            formatted.append(digits.charAt(i));
            if ((i + 1) % 4 == 0 && i != digits.length() - 1) {
                formatted.append("-");
            }
        }
        return formatted.toString();
    }

    private String formatExpiryDate(String input) {
        String digits = input.replaceAll("\\D", "");

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            formatted.append(digits.charAt(i));
            if (i == 1 && i != digits.length() - 1) {
                formatted.append("/");
            }
        }
        return formatted.toString();
    }
}
