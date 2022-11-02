package com.wotle.wotle.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wotle.wotle.R;

public class EmergencyContact extends AppCompatActivity {
    Button Buttontambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);


        Buttontambah = findViewById(R.id.tambahKontak);
        Buttontambah.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Form_EmergencyContact.class);
                startActivity(i);
                finish();
            }
        });

    }
}