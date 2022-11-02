package com.wotle.wotle.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wotle.wotle.R;

public class EquipmentDriver extends AppCompatActivity {

    private ImageView backProfileDriverPerlengkapan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_driver);
        backProfileDriverPerlengkapan = findViewById(R.id.backProfileDriverPerlengkapan);


        backProfileDriverPerlengkapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EquipmentDriver.this, MyProfile.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EquipmentDriver.this, MyProfile.class));
        finish();
    }
}