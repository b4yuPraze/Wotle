package com.wotle.wotle.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.Customer.Customer;
import com.wotle.wotle.GenerateOTP.OTP;
import com.wotle.wotle.R;

public class OneTimePassword extends AppCompatActivity {

    TextView otp1, otp2, otp3, otp4, resendEmail;
    Button submitOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_password);

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        submitOtp = findViewById(R.id.submitOtp);
        resendEmail = findViewById(R.id.kirimUlangOtp);

        Intent intent = getIntent();
        String email_from_register = intent.getStringExtra("email");
        String email_from_login = intent.getStringExtra("email");
        
        String otp = String.valueOf(new OTP().oneTimePassword());

        if (email_from_login.equals("") || email_from_login != null){
            new PrefManager(this).verifEmail(email_from_login, otp);
        }else if (email_from_register.equals("") || email_from_register != null){
            new PrefManager(this).verifEmail(email_from_register, otp);
        }

        submitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String combineOTP = otp1.getText() + "" + otp2.getText() + otp3.getText() + otp4.getText();
                if (combineOTP.equals(otp)){
                    if (!email_from_register.equals("") || email_from_register != null){
                        new PrefManager(OneTimePassword.this).updateVerifEmail(email_from_register);
                        Toast.makeText(OneTimePassword.this, "Kode valid", Toast.LENGTH_SHORT).show();
                    }else if (!email_from_login.equals("") || email_from_login != null){
                        Toast.makeText(OneTimePassword.this, "Kode valid", Toast.LENGTH_SHORT).show();
                        new PrefManager(OneTimePassword.this).updateVerifEmail(email_from_login);
                    }else{
                        Toast.makeText(OneTimePassword.this, "Error, tidak ada email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(OneTimePassword.this, "Kode tidak valid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email_from_login.equals("") || email_from_login != null){
                    new PrefManager(OneTimePassword.this).verifEmail(email_from_login, otp);
                }else if (email_from_register.equals("") || email_from_register != null){
                    new PrefManager(OneTimePassword.this).verifEmail(email_from_register, otp);
                }
            }
        });
        

    }
}